from pos import Pos
from controlPoint import ControlPoint
from unitView import UnitView
from field import Field
from logger import debug_print

import time
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
        global runCounter
        runCounter += 1
        debug_print(f"starting------ {self.id}, {self.team}, {self.type}, RUN:{runCounter}")
        debug_print(f"current pos ={self.field.pos.val()}")
        neighbours = []
        for f in self.seenFields:
            #debug_print(f"f: {f.pos.val()}, self: {self.field.val()}")
            if abs(f.pos.x - self.field.pos.x) <= 1 and abs(f.pos.y - self.field.pos.y) <= 1:
                #debug_print("adding new neightbour")
                neighbours.append(f)
        debug_print(f"done interating over seenFields {len(neighbours)}")
        for n in neighbours:
            debug_print(f"current n to check:{n.val()}, {self.field.val()}, {n.type}")
            if n.type == "GRASS" and n.pos != self.field.pos:
                print("move", self.id, n.pos.x, n.pos.y)
                debug_print(f"sending this message: |move {self.id} {n.pos.x} {n.pos.y}|")
                break
        debug_print("end of iterating over neighbours")

    def heuristicMove(self):
        pass

    def astar(dest):
        openList = []
        closedList = []

        openList.append(self.pos)

        while not openList:
            current = openList[0]
            currentIndex = 0
            for idx, item in enumerate(open_list):
                if item.f < current.f:
                    current = item
                    currentIndex = idx
            
            openList.pop(currentIndex)
            closedList.append(current)

            if current.pos == dest.pos:
                path = []
                curr = current
                while curr is not None:
                    path.append(curr.pos)
                    curr = curr.parent
                return path[::-1]

            adjacentFields = []

            for f in self.seenFields:
                if abs(f.pos.x - self.field.pos.x) <= 1 and abs(f.pos.y - self.field.pos.y) <= 1 and f.type in self.steppables:
                    f.parent = current
                    adjacentFields.append(f)

            for a in adjacentFields:

                for c in closedList:
                    if a == c:
                        continue


                a.g = current.g + 1
                a.h = ((a.pos[0] - dest.pos[0]) ** 2) + ((a.pos[1] - dest.pos[1])**2)
                a.f = a.g + a.h

                for o in openList:
                    if a == o and a.g > o.g:
                        continue

                openList.append(a)

