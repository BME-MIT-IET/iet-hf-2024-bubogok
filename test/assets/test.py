from typing import List

i = 0
while(True):
	line = input()
	line = line.replace('[', '', 1)
	line = line.replace(']', '', 1)
	id = line.split(", ")
	print(id[0], i, i) 
	i += 1

