from unit import Unit
from unitView import UnitView
from pos import Pos
from logger import debug_print

import math
import numpy as np
import random
import matplotlib.pyplot as plt
import numpy as np


actions = ["attack", "retreat"]

resolution = 3

# id - cp
collectiveControlPoints = {}

# id - unit
collectiveEnemys = {}

# AxB
mapParts = np.zeros((3, 3, 2), dtype=int)



def collectiveSight(units):
	for u in units:
		for other in u.seenUnits:
			if(other.id not in collectiveEnemys.keys() and other.team != u.team):
				collectiveEnemys[other.id] = other
		for cp in u.seenControlPoints:
			if(cp.id not in collectiveControlPoints.keys()):
				collectiveControlPoints[cp.id] = cp
	debug_print(f"enemys: {collectiveEnemys}, cps: {collectiveControlPoints}")

# fel kellene bontani a pályát részekre
def participator():
	global collectiveEnemys
	global mapParts
	for enemyId in collectiveEnemys:
		x = collectiveEnemys[enemyId].pos.x
		y = collectiveEnemys[enemyId].pos.y
		if(x >= 0 and x <= 10 and y >= 0 and y <= 10):
			mapParts[0][0][0] += 1
			# mapParts[0][0][0].append(collectiveEnemys[enemyId])
		elif(x >= 0 and x <= 10 and y > 10 and y <= 20):
			mapParts[0][1][0] += 1
		elif(x >= 0 and x <= 10 and y > 20 and y <= 30):
			mapParts[0][2][0] += 1

		elif(x > 10 and x <= 20 and y >= 0 and y <= 10):
			mapParts[1][0][0] += 1
		elif(x > 10 and x <= 20 and y > 10 and y <= 20):
			mapParts[1][1][0] += 1
		elif(x > 10 and x <= 20 and y > 20 and y <= 30):
			mapParts[1][2][0] += 1

		elif(x > 20 and x <= 30 and y >= 0 and y <= 10):
			mapParts[0][0][0] += 1
		elif(x > 20 and x <= 30 and y > 10 and y <= 20):
			mapParts[0][1][0] += 1
		elif(x > 20 and x <= 30 and y > 20 and y <= 30):
			mapParts[0][2][0] += 1


	for cpId in collectiveControlPoints:
		x = collectiveControlPoints[cpId].pos.x
		y = collectiveControlPoints[cpId].pos.y
		if(x >= 0 and x <= 10 and y >= 0 and y <= 10):
			mapParts[0][0][1] += 1
			# mapParts[0][0][0].append(collectiveEnemys[enemyId])
		elif(x >= 0 and x <= 10 and y > 10 and y <= 20):
			mapParts[0][1][1] += 1
		elif(x >= 0 and x <= 10 and y > 20 and y <= 30):
			mapParts[0][2][1] += 1

		elif(x > 10 and x <= 20 and y >= 0 and y <= 10):
			mapParts[1][0][1] += 1
		elif(x > 10 and x <= 20 and y > 10 and y <= 20):
			mapParts[1][1][1] += 1
		elif(x > 10 and x <= 20 and y > 20 and y <= 30):
			mapParts[1][2][1] += 1

		elif(x > 20 and x <= 30 and y >= 0 and y <= 10):
			mapParts[0][0][1] += 1
		elif(x > 20 and x <= 30 and y > 10 and y <= 20):
			mapParts[0][1][1] += 1
		elif(x > 20 and x <= 30 and y > 20 and y <= 30):
			mapParts[0][2][1] += 1

	debug_print(mapParts)




# itt kellene tömöríteni a tudást nesekbe
def asdf():
	pass


def tLAction(units):
	global mapParts
	collectiveSight(units)
	participator()

	mapParts = np.zeros((3, 3, 2), dtype=int)
	return "endTurn"