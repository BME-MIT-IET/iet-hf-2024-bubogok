from unit import Unit
from unitView import UnitView
from pos import Pos
from logger import debug_print
from mapPart import MapPart


import math
import numpy as np
import random
import matplotlib.pyplot as plt
import numpy as np


size = 0
resolution = 6

totalCounter = 1
runcounter = 0
total_episode_reward = 0
total_couter_all = list()
total_rewards_per_episode = list()


#maximum of iteration per episode
max_iter_episode = 50
#initialize the exploration probability to 1
exploration_proba = 0.1
#exploartion decreasing decay for exponential decreasing
exploration_decreasing_decay = 0.001
# minimum of exploration proba
min_exploration_proba = 0.01
#discounted factor
gamma = 0.99
#learning rate
lr = 0.01



# menekülés esetleg

			# 0 		1 			2
actions = ["attack", "retreat", "spread"]

# id - cp
collectiveControlPoints = {}

# id - unit
collectiveUnits = {}

# AxB
mapParts = [[MapPart(i, j) for i in range(resolution)] for j in range(resolution)]


g1Qtable = np.zeros((resolution*resolution, len(actions)))
g2Qtable = np.zeros((resolution*resolution, len(actions)))

q_tables = {}
q_tables[0] = g1Qtable
q_tables[1] = g2Qtable


group1 = []
group2 = []
groups = []


def collectiveSight(units):
	global collectiveUnits
	global collectiveControlPoints
	collectiveUnits = {}
	collectiveControlPoints = {}
	for u in units:
		if(u.id not in collectiveUnits.keys()):
				collectiveUnits[u.id] = UnitView([u.field.pos.x, u.field.pos.y, u.team, u.type, u.id, u.health])
		for other in u.seenUnits:
			if(other.id not in collectiveUnits.keys()):
				collectiveUnits[other.id] = other

		for cp in u.seenControlPoints:
			if(cp.id not in collectiveControlPoints.keys()):
				collectiveControlPoints[cp.id] = cp
	debug_print(f"units: {collectiveUnits}, cps: {collectiveControlPoints}")

# fel kellene bontani a pályát részekre, és minden térrészbe a megfelelő egységeket és kontollpontokat beletenni
def participator():
	global collectiveUnits
	global mapParts
	for enemyId in collectiveUnits:
		x = collectiveUnits[enemyId].pos.x // int((size//resolution))
		y = collectiveUnits[enemyId].pos.y // int((size//resolution))
		# debug_print(f"mpz: {int((size//resolution))} ### {collectiveUnits[enemyId].pos.x} -> {x}, {collectiveUnits[enemyId].pos.y} -> {y}")
		mapParts[x][y].addUnit(collectiveUnits[enemyId])


	for cpId in collectiveControlPoints:
		x = collectiveUnits[enemyId].pos.x // int((size//resolution))
		y = collectiveUnits[enemyId].pos.y // int((size//resolution))
		mapParts[x][y].addCp(collectiveControlPoints[cpId])

	# for r in mapParts:
	# 	for c in r:
	# 		debug_print(c.printStatus())


def makeGroups(units):
	global groups
	groups = []
	global group1
	group1 = list()
	closest = units[0]
	if(len(units) < 2):
		group1.append(units[0])
		groups.append(group1)
		return
	global group2
	group2 = list()
	furthest = units[1]
	closestDist = closest.field.pos.dist(Pos([0, 0]))
	furthestDist = furthest.field.pos.dist(Pos([0, 0]))
	for u in units:
		currDist = u.field.pos.dist(Pos([0, 0]))
		if(currDist < closestDist):
			closest = u
			closestDist = currDist
		if(currDist > furthestDist):
			furthest = u
			furthestDist = currDist
	group1.append(closest)
	group2.append(furthest)
	for u in units:
		if(u == closest or u == furthest):
			continue
		else:
			dist1 = u.field.pos.dist(closest.field.pos)
			dist2 = u.field.pos.dist(furthest.field.pos)
			if(dist1 < dist2):
				group1.append(u)
			else:
				group2.append(u)
	
	groups.append(group1)
	groups.append(group2)

prevState_idx = None
prevAction = None
reward = 0

def commanndeer(group, groupIndex):
	commandQueue = list()
	for u in group:
		if(u.actionPoints != 0):
			break
	else:
		return None

	Q_table = q_tables[groupIndex]
	current_state_idx = toidx(group[0])
	global prevState_idx
	global prevAction
	global reward
	if(prevState_idx is not None):
		debug_print(f"idx: {prevState_idx}")
		Q_table[prevState_idx[0], prevAction] = (1-lr) * Q_table[prevState_idx[0], prevAction] + lr*(reward + gamma*max(Q_table[current_state_idx[0],:] - Q_table[prevState_idx[0], prevAction]))


	prevState_idx = current_state_idx

	if np.random.uniform(0,1) < exploration_proba:
		action = random.randint(0, 1)
	else:
		action = np.argmax(Q_table[current_state_idx[0],:])
	debug_print(f"groupIndex: {groupIndex}, action: {action}")

	prevAction = action
	reward = rewardCalc(group, action)

	if(action == 0):
		commandQueue += attack(group)
	elif(action == 1):
		commandQueue += retreat(group)
	return commandQueue


def attack(group):
	commands = list()
	collectiveEnemys = []
	global collectiveUnits
	debug_print(f"cu: {collectiveUnits}")
	for key in collectiveUnits:
		debug_print(f"collectiveUnits[key].team: {collectiveUnits[key].team}, group[0].team: {group[0].team}")
		if(collectiveUnits[key].team != group[0].team):
			collectiveEnemys.append(collectiveUnits[key])
	debug_print(f"collectiveEnemys: {collectiveEnemys}")
	if(len(collectiveEnemys) != 0):
		for unit in group:
			if (unit.field.pos.euclDist(collectiveEnemys[0].pos) < unit.shootRange):
				commands.append(f"shoot {unit.id} {collectiveEnemys[0].pos.x} {collectiveEnemys[0].pos.y}")
			move = unit.astar(collectiveEnemys[0].pos)
			if(move is not None):
				commands.append(f"move {unit.id} {move.pos.x} {move.pos.y}")
			else:
				debug_print(f"move is None {unit.id}")
	else:
		for unit in group:
			move = unit.dummyMove()
			if(move is not None):
				commands.append(f"move {unit.id} {move.pos.x} {move.pos.y}")
			else:
				debug_print(f"move is None {unit.id}")

	return commands



def retreat(group):
	commands = list()
	if(len(collectiveControlPoints) != 0):
		for unit in group:
			move = unit.astar(collectiveControlPoints[0].pos)
			if(move is not None):
				commands.append(f"move {unit.id} {move.pos.x} {move.pos.y}")
			else:
				debug_print(f"move is None {unit.id}")
	else:
		for unit in group:
			move = unit.dummyMove()
			if(move is not None):
				commands.append(f"move {unit.id} {move.pos.x} {move.pos.y}")
			else:
				debug_print(f"move is None {unit.id}")

	return commands

def spread(group):
	commands = list()


def toidx(unit):
	x = (unit.field.pos.x//int((size//resolution)))
	y = (unit.field.pos.y//int((size//resolution)))
	return x * resolution + y, mapParts[x][y].getTeamScore(unit.team)

def fromidx(idx):
	return Pos([idx // resolution, idx % resolution])

def rewardCalc(group, action):
	# reward számolásnál figyelembe kellene venni, hogy milyen akciór végeztünk
	if(len(group[0].seenControlPoints) == 0):
		return -10
	currDist = group[0].field.pos.dist(group[0].seenControlPoints[0].pos)
	if(group[0].field.pos.euclDist(group[0].seenControlPoints[0].pos) < group[0].seenControlPoints[0].size):
		return 30
	return (30 - currDist)


def tLAction(units):
	global mapParts
	global runcounter
	global total_episode_reward
	global totalCounter
	collectiveSight(units)
	participator()
	makeGroups(units)
	teamCommands = list()
	# debug_print("mapParts:")
	# for r in mapParts:
	# 	for c in r:
	# 		debug_print(c.printStatus())
	# debug_print("mapPartsDONE")
	for i, g in enumerate(groups):
		debug_print(f"groups: {[[str(unit) for unit in group] for group in groups]}")
		teamCommands += commanndeer(g, i)


	total_episode_reward = total_episode_reward + reward
	
	totalCounter += 1
	total_couter_all.append(totalCounter)
	total_rewards_per_episode.append(total_episode_reward)
	runcounter += 1

	if(totalCounter > 1000):
		plotData()

	if(runcounter == max_iter_episode):
		runcounter = 0
		total_episode_reward = 0
		teamCommands.append("reset")

	# ez itt egy picit kókány
	mapParts = [[MapPart(i, j) for i in range(resolution)] for j in range(resolution)]
	teamCommands.append("endTurn")
	debug_print(teamCommands)
	return teamCommands


def tlInit(s):
	global size 
	size = s;


def plotData():
	Q_table = q_tables[0]
	graph, (Q_plot1, reward_plot, Q_plot2) = plt.subplots(1, 3)
	Q_plot1.set_title("Q_table for group1 values heatmap")
	Q_plot1.imshow(Q_table, cmap='binary', interpolation='nearest')
	reward_plot.set_title("total rewards / episode")
	reward_plot.plot(total_couter_all, total_rewards_per_episode, color='black', linewidth=0.5)
	Q_table = q_tables[1]
	Q_plot2.set_title("Q_table for group2 values heatmap")
	Q_plot2.imshow(Q_table, cmap='binary', interpolation='nearest')
	graph.tight_layout()
	plt.show()