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
        global runCounter
        runCounter += 1
        debug_print(f"starting------ {self.id}, {self.team}, {self.type}, RUN:{runCounter}")
        debug_print(f"current pos ={self.field.pos.val()}")
        path = self.astar(self.seenControlPoints[0])
        if(path is None):
            print("move", self.id, self.field.pos.x, self.field.pos.y)
        elif(len(path) == 1):
            print("move", self.id, path[0].pos.x, path[0].pos.y)
        else:
            print("move", self.id, path[-2].pos.x, path[-2].pos.y)

        

    def astar(self, dest):
        debug_print("in A*")
        closedSet = []
        openSet = []
        path = []
        openSet.append(self.field)
        while(len(openSet) > 0):
            debug_print("A* in biggest loop")
            winner = openSet[0]
            for f in openSet:
                if(f.f < winner.f):
                    winner = f

            current = winner
            debug_print(current.getPos())
            if(current.pos == dest.pos):
                debug_print("A* found the end!")
                temp = current
                path.append(temp)
                while(temp.parent is not None):
                    path.append(temp.parent)
                    temp = temp.parent
                debug_print("openSet:", openSet)
                debug_print("path:", len(path))
                return path

            # self.removeField(openSet, current)
            openSet.remove(current)
            closedSet.append(current)

            neighbours = []
            for f in self.seenFields:
                if abs(f.pos.x - current.pos.x) <= 1 and abs(f.pos.y - current.pos.y) <= 1:
                    neighbours.append(f)

            for n in neighbours:
                debug_print("N2check:", n.getPos())
                if(not n in closedSet and n.type in self.steppables):
                    tempG = current.g + max(abs(n.pos.x - current.pos.x), abs(n.pos.y - current.pos.y))
                    newPath = False
                    if n in openSet:
                        debug_print("ASDF")
                        if(tempG < n.g):
                            n.g = tempG
                            newPath = True
                            # openSet.append(n)
                    else:
                        n.g = tempG
                        newPath = True
                        debug_print("appending", n.getPos(), " to openSet")
                        openSet.append(n)
                    if(newPath):
                        debug_print("HERE")
                        n.h = max(abs(n.pos.x - dest.pos.x), abs(n.pos.y - dest.pos.y))
                        n.f = n.g + n.h
                        n.parent = current
                


