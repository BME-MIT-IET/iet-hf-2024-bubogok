from pos import Pos
from controlPoint import ControlPoint
from unitView import UnitView
from field import Field
from logger import debug_print

import time
import math
import os
from datetime import datetime

runCounter = 0

class Unit:
    def __init__(self, id, tipus, posWType, seenFields, seenUnits, seenControlPoints, health, ammo, fuel, actionPoints, team):
        self.id = id
        self.type = tipus
        self.field = Field(posWType)
        self.health = health
        self.ammo = ammo
        self.fuel = fuel
        self.seenFields = []
        for f in seenFields:
            self.seenFields.append(Field(f))
        self.seenUnits = []
        for u in seenUnits:
            self.seenUnits.append(UnitView(u))

        self.seenControlPoints = []
        for cp in seenControlPoints:
            self.seenControlPoints.append(ControlPoint(cp))
        self.actionPoints = actionPoints
        self.team = team
        descriptorFile = open(f"{os.getcwd()}/desciptors/{self.type}.txt", "r")
        self.maxHealth = int(descriptorFile.readline())
        self.viewRange = int(descriptorFile.readline())
        self.shootRange = int(descriptorFile.readline())
        self.damage = int(descriptorFile.readline())
        self.maxAmmo = int(descriptorFile.readline())
        self.maxFuel = int(descriptorFile.readline())
        self.consumption = int(descriptorFile.readline())
        self.maxActionPoints = int(descriptorFile.readline())
        self.price = int(descriptorFile.readline())
        dummy1 = int(descriptorFile.readline())
        self.steppables = []
        for i in range(dummy1):
            dummy2 = descriptorFile.readline()
            self.steppables.append(dummy2[:-1])
        debug_print(f"{self.id}, {self.type}, {self.steppables}")
        descriptorFile.close()

    def update(self, posWType, seenFields, seenUnits, seenControlPoints, health, ammo, fuel, actionPoints):
        self.field = Field(posWType)
        self.seenFields = []
        for f in seenFields:
            self.seenFields.append(Field(f))
        self.seenUnits = []
        for u in seenUnits:
            self.seenUnits.append(UnitView(u))
        self.seenControlPoints = []
        for cp in seenControlPoints:
            self.seenControlPoints.append(ControlPoint(cp))
        self.health = health
        self.ammo = ammo
        self.fuel = fuel
        self.actionPoints = actionPoints
        



    def testWriteOut(self):
        self.pos.out()


    def dummyMove(self):
        neighbours = []
        for f in self.seenFields:
            if(abs(f.pos.x - self.field.pos.x) <= 1 and abs(f.pos.y - self.field.pos.y) <= 1):
                neighbours.append(f)
        for n in neighbours:
            if(n.type in self.steppables and n.pos != self.field.pos
                and f.pos not in [u.pos for u in self.seenUnits]):
                return f"move {self.id} {n.pos.x} {n.pos.y}"

    def heuristicAction(self):
        global runCounter
        runCounter += 1
        debug_print(f"starting------ {self.id}, {self.team}, {self.type}, {self.field.getPos()}, {self.actionPoints},  RUN:{runCounter}")
        if(self.actionPoints == 0):
            return None
        debug_print(self.id, self.actionPoints)
        if(self.seenUnits is not None and self.ammo > 0):
            for unit in self.seenUnits:
                if(unit.team != self.team):
                    return f"shoot {self.id} {unit.pos.x} {unit.pos.y}"

        if(self.fuel > 0):
            if(len(self.seenControlPoints) != 0):
                path = self.astar(self.seenControlPoints[0])
                if(path is None): #astar nem látja a dest-et
                    # nem így!!!
                    return None
                elif(len(path) == 1): # már a destben vagyunk
                    return None
                else:
                    return f"move {self.id} {path[-2].pos.x} {path[-2].pos.y}"
            else:
                return self.dummyMove()
        return None

        

    def astar(self, dest):
        closedSet = []
        openSet = []
        path = []
        openSet.append(self.field)
        while(len(openSet) > 0):
            winner = openSet[0]
            for f in openSet:
                if(f.f < winner.f):
                    winner = f

            current = winner
            if(current.pos == dest.pos):
                temp = current
                path.append(temp)
                while(temp.parent is not None):
                    path.append(temp.parent)
                    temp = temp.parent
                return path

            # self.removeField(openSet, current)
            openSet.remove(current)
            closedSet.append(current)

            neighbours = []
            for f in self.seenFields:
                if(abs(f.pos.x - current.pos.x) <= 1 and abs(f.pos.y - current.pos.y) <= 1
                    and f.pos not in [u.pos for u in self.seenUnits]):
                    neighbours.append(f)

            for n in neighbours:
                if(not n in closedSet and n.type in self.steppables):
                    tempG = current.g + max(abs(n.pos.x - current.pos.x), abs(n.pos.y - current.pos.y))
                    newPath = False
                    if n in openSet:
                        if(tempG < n.g):
                            n.g = tempG
                            newPath = True
                    else:
                        n.g = tempG
                        newPath = True
                        openSet.append(n)
                    if(newPath):
                        n.h = max(abs(n.pos.x - dest.pos.x), abs(n.pos.y - dest.pos.y))
                        n.f = n.g + n.h
                        n.parent = current
                


