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

actions = ["attack", "retreat"]

# id - cp
collectiveControlPoints = {}

# id - unit
collectiveEnemys = {}

# AxB
mapParts = [[MapPart(i, j) for i in range(3)] for j in range(3)]


TLQtable = np.zeros((3*3, len(actions)))

group1 = []
group2 = []


def collectiveSight(units):
	for u in units:
		for other in u.seenUnits:
			if(other.id not in collectiveEnemys.keys() and other.team != u.team):
				collectiveEnemys[other.id] = other
		for cp in u.seenControlPoints:
			if(cp.id not in collectiveControlPoints.keys()):
				collectiveControlPoints[cp.id] = cp
	debug_print(f"enemys: {collectiveEnemys}, cps: {collectiveControlPoints}")

# fel kellene bontani a pályát részekre, és minden térrészbe a megfelelő egységeket és kontollpontokat beletenni
def participator():
	global collectiveEnemys
	global mapParts
	for enemyId in collectiveEnemys:
		x = collectiveEnemys[enemyId].pos.x
		y = collectiveEnemys[enemyId].pos.y
		if(x >= 0 and x <= 10 and y >= 0 and y <= 10):
			mapParts[0][0].addUnit(collectiveEnemys[enemyId])
		elif(x >= 0 and x <= 10 and y > 10 and y <= 20):
			mapParts[0][1].addUnit(collectiveEnemys[enemyId])
		elif(x >= 0 and x <= 10 and y > 20 and y <= 30):
			mapParts[0][2].addUnit(collectiveEnemys[enemyId])

		elif(x > 10 and x <= 20 and y >= 0 and y <= 10):
			mapParts[1][0].addUnit(collectiveEnemys[enemyId])
		elif(x > 10 and x <= 20 and y > 10 and y <= 20):
			mapParts[1][1].addUnit(collectiveEnemys[enemyId])
		elif(x > 10 and x <= 20 and y > 20 and y <= 30):
			mapParts[1][2].addUnit(collectiveEnemys[enemyId])

		elif(x > 20 and x <= 30 and y >= 0 and y <= 10):
			mapParts[0][0].addUnit(collectiveEnemys[enemyId])
		elif(x > 20 and x <= 30 and y > 10 and y <= 20):
			mapParts[0][1].addUnit(collectiveEnemys[enemyId])
		elif(x > 20 and x <= 30 and y > 20 and y <= 30):
			mapParts[0][2].addUnit(collectiveEnemys[enemyId])


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


def commanndeer(group):
	pass


def tLAction(units):
	global mapParts
	collectiveSight(units)
	participator()
	makeGroups(units)
	debug_print("g1:")
	for u in group1:
		debug_print(u.id)
	debug_print("g2:")
	for u in group2:
		debug_print(u.id)
	# debug_print(f"g1:{u.id for u in group1}, g2:{u.id for u in group2}")


# ez itt egy picit kókány
	mapParts = [[MapPart(i, j) for i in range(3)] for j in range(3)]
	return "endTurn"