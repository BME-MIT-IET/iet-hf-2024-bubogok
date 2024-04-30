from unit import Unit
from unitView import UnitView
from pos import Pos
from logger import debug_print

import math
import numpy as np
import random
import matplotlib.pyplot as plt
import numpy as np



collectiveControlPoints = {}
collectiveEnemys = {}


def collectiveSight(units):
	for u in units:
		for other in u.seenUnits:
			if(other.id not in collectiveEnemys.keys() and other.team != u.team):
				collectiveEnemys[other.id] = other
		for cp in u.seenControlPoints:
			if(cp.id not in collectiveControlPoints.keys()):
				collectiveControlPoints[cp.id] = cp
	debug_print(f"enemys: {collectiveEnemys}, cps: {collectiveControlPoints}")
	return None



def tLAction(units):
	collectiveSight(units)
	return "endTurn"