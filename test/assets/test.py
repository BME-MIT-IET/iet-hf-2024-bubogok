from typing import List
import json




class Unit:
	def __init__(self, id, pos):
		self.id = id
		self.pos = pos





i = 0
while(True):
	line = input()

	#asdf = json.loads(line)
	#print(asdf["units"], file=2)
	line = line.replace('[', '', 1)
	line = line.replace(']', '', 1)
	id = line.split(", ")
	print(id[0], i, 0) 
	i += 1



