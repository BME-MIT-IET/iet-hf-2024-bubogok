from pos import Pos
from unitView import UnitView
from controlPoint import ControlPoint



class MapPart:
	def __init__(self, x, y):
		self.x = x
		self.y = y
		self.uvs = list()
		self.cps = list()
	def addUnit(self, uv):
		self.uvs.append(uv)

	def addCp(self, cp):
		self.cps.append(cp)

	def printStatus(self):
		return f"{self.uvs},  {self.cps}"

	def getTeamScore(self, teamName):
		teamScore = 0
		for uv in self.uvs:
			if(uv.team == teamName):
				teamScore += uv.health
			else:
				teamScore -= uv.health
		return teamScore