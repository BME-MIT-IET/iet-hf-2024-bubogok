from typing import List


'''
for i in range(3):
    with open("asdf.txt", "w") as f:
        line = input()
        line = line.replace(";", "")
        f.write(line)
'''

class Unit:
    def __init__(self, id, pos, seenFields, seenUnits, health, ammo, fuel,  team):
        self.id = id
        self.pos = pos
        self.health = health
        self.ammo = ammo
        self.fuel = fuel
        self.seenFields = seenFields
        self.seenUnits = seenUnits
        self.team = team



with open("bsdf.txt", "r") as f:
    #incoming = f.read()
    #incoming = incoming.replace(";", "")
    
    testID = int(f.readline().replace("\n", ""))
    testpos = f.readline()
    testseenFields = int(f.readline())
    testseenUnits = int(f.readline())
    testhealth = int(f.readline())
    testammo = int(f.readline())
    testfuel = int(f.readline())
    testteam = int(f.readline())
    testUnit = Unit(testID, testpos, testseenFields, testseenUnits, testhealth, testammo, testfuel, testteam)
    print(testpos)
    
    
    
    
    
    
    
    
    
    
    
