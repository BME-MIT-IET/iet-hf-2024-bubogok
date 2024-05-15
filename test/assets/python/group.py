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



class Group:
	def __init__(self, name, gl):
		self.name = name
		self.groupLeader = gl
		self.units = list()

	def attack(self):
		for u in self.units:
			pass

	def explore(self):
		for u in self.units:
			pass

	def retreat(self):
		for u in self.units:
			pass

	def compress(self):
		for u in self.units:
			pass