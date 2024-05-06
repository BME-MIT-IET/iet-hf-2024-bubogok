from pos import Pos
from unitView import UnitView
from controlPoint import ControlPoint



class MapPart:
	def __init__(self, x, y):
		self.x = x
		self.y = y
		self.uvs = list()
		self.cps = list()
		self.score = 0

	def addUnit(self, uv):
		self.uvs.append(uv)
		if(uv.team == "white"):
			self.score += uv.health
		else:
			self.score -= uv.health

	def addCp(self, cp):
		self.cps.append(cp)

	def printStatus(self):
		return f"{self.uvs},  {self.cps}, {self.score}"