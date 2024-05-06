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




#maximum of iteration per episode
max_iter_episode = 30
#initialize the exploration probability to 1
exploration_proba = 1
#exploartion decreasing decay for exponential decreasing
exploration_decreasing_decay = 0.001
# minimum of exploration proba
min_exploration_proba = 0.01
#discounted factor
gamma = 0.99
#learning rate
lr = 0.01






actions = ["attack", "retreat"]

# id - cp
collectiveControlPoints = {}

# id - unit
collectiveUnits = {}

# AxB
mapParts = [[MapPart(i, j) for i in range(3)] for j in range(3)]


g1Qtable = np.zeros((3*3, len(actions)))
g2Qtable = np.zeros((3*3, len(actions)))

q_tables = {}
q_tables[0] = g1Qtable
q_tables[1] = g2Qtable


group1 = []
group2 = []
groups = [group1, group2]


def collectiveSight(units):
	for u in units:
		if(u.id not in collectiveUnits.keys()):
				collectiveUnits[u.id] = UnitView([u.field.pos.x, u.field.pos.y, u.team, u.type, u.id, u.health])
		for other in u.seenUnits:
			if(other.id not in collectiveUnits.keys()):
				collectiveUnits[other.id] = other

		for cp in u.seenControlPoints:
			if(cp.id not in collectiveControlPoints.keys()):
				collectiveControlPoints[cp.id] = cp
	debug_print(f"enemys: {collectiveUnits}, cps: {collectiveControlPoints}")

# fel kellene bontani a pályát részekre, és minden térrészbe a megfelelő egységeket és kontollpontokat beletenni
def participator():
	global collectiveUnits
	global mapParts
	for enemyId in collectiveUnits:
		x = collectiveUnits[enemyId].pos.x
		y = collectiveUnits[enemyId].pos.y
		if(x >= 0 and x <= 10 and y >= 0 and y <= 10):
			mapParts[0][0].addUnit(collectiveUnits[enemyId])
		elif(x >= 0 and x <= 10 and y > 10 and y <= 20):
			mapParts[0][1].addUnit(collectiveUnits[enemyId])
		elif(x >= 0 and x <= 10 and y > 20 and y <= 30):
			mapParts[0][2].addUnit(collectiveUnits[enemyId])

		elif(x > 10 and x <= 20 and y >= 0 and y <= 10):
			mapParts[1][0].addUnit(collectiveUnits[enemyId])
		elif(x > 10 and x <= 20 and y > 10 and y <= 20):
			mapParts[1][1].addUnit(collectiveUnits[enemyId])
		elif(x > 10 and x <= 20 and y > 20 and y <= 30):
			mapParts[1][2].addUnit(collectiveUnits[enemyId])

		elif(x > 20 and x <= 30 and y >= 0 and y <= 10):
			mapParts[0][0].addUnit(collectiveUnits[enemyId])
		elif(x > 20 and x <= 30 and y > 10 and y <= 20):
			mapParts[0][1].addUnit(collectiveUnits[enemyId])
		elif(x > 20 and x <= 30 and y > 20 and y <= 30):
			mapParts[0][2].addUnit(collectiveUnits[enemyId])


	for cpId in collectiveControlPoints:
		x = collectiveControlPoints[cpId].pos.x
		y = collectiveControlPoints[cpId].pos.y
		if(x >= 0 and x <= 10 and y >= 0 and y <= 10):
			mapParts[0][0].addCp(collectiveControlPoints[cpId])
		elif(x >= 0 and x <= 10 and y > 10 and y <= 20):
			mapParts[0][1].addCp(collectiveControlPoints[cpId])
		elif(x >= 0 and x <= 10 and y > 20 and y <= 30):
			mapParts[0][2].addCp(collectiveControlPoints[cpId])

		elif(x > 10 and x <= 20 and y >= 0 and y <= 10):
			mapParts[1][0].addCp(collectiveControlPoints[cpId])
		elif(x > 10 and x <= 20 and y > 10 and y <= 20):
			mapParts[1][1].addCp(collectiveControlPoints[cpId])
		elif(x > 10 and x <= 20 and y > 20 and y <= 30):
			mapParts[1][2].addCp(collectiveControlPoints[cpId])

		elif(x > 20 and x <= 30 and y >= 0 and y <= 10):
			mapParts[0][0].addCp(collectiveControlPoints[cpId])
		elif(x > 20 and x <= 30 and y > 10 and y <= 20):
			mapParts[0][1].addCp(collectiveControlPoints[cpId])
		elif(x > 20 and x <= 30 and y > 20 and y <= 30):
			mapParts[0][2].addCp(collectiveControlPoints[cpId])

	# for r in mapParts:
	# 	for c in r:
	# 		debug_print(c.printStatus())


def makeGroups(units):
	if(len(units) < 2):
		return None
	global group1
	group1 = list()
	global group2
	group2 = list()
	closest = units[0]
	furthest = units[1]
	group1.append(closest)
	group2.append(furthest)
	closestDist = math.sqrt((closest.field.pos.x**2 + closest.field.pos.y**2))
	furthestDist = math.sqrt((furthest.field.pos.x**2 + furthest.field.pos.y**2))
	for u in units:
		currDist = math.sqrt((u.field.pos.x**2 + u.field.pos.y**2))
		if(currDist < closestDist):
			closest = u
			closestDist = currDist
		if(currDist > furthestDist):
			furthest = u
			furthestDist = currDist
	for u in units:
		if(u == closest or u == furthest):
			continue
		else:
			dist1 = abs(math.sqrt((u.field.pos.x**2 + u.field.pos.y**2)) - closestDist)
			dist2 = abs(math.sqrt((u.field.pos.x**2 + u.field.pos.y**2)) - furthestDist)
			if(dist1 < dist2):
				group1.append(u)
			else:
				group2.append(u)


def commanndeer(group, groupIndex):
	for u in group:
		if(u.actionPoints != 0):
			break
	else:
		return None
	Q_table = q_tables[groupIndex]
	global exploration_proba
	global runcounter
	global total_episode_reward
	global totalCounter
	debug_print(exploration_proba, runcounter, totalCounter)


def tLAction(units):
	global mapParts
	collectiveSight(units)
	participator()
	makeGroups(units)
	debug_print("mapParts:")
	for r in mapParts:
		for c in r:
			debug_print(c.printStatus())
	debug_print("mapPartsDONE")

	# for i, g in enumerate(groups):
	# 	commanndeer(g, i)



	# debug_print("g1:")
	# for u in group1:
	# 	debug_print(u.id)
	# debug_print("g2:")
	# for u in group2:
	# 	debug_print(u.id)
	# debug_print(f"g1:{u.id for u in group1}, g2:{u.id for u in group2}")


# ez itt egy picit kókány
	mapParts = [[MapPart(i, j) for i in range(3)] for j in range(3)]
	return "endTurn"