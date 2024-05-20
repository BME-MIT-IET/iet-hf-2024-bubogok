from pos import Pos
from controlPoint import ControlPoint
from unitView import UnitView
from field import Field
from logger import debug_print

import time
import math
import os
from datetime import datetime

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
        descriptorFile = open(f"{os.getcwd()}/assets/descriptors/{self.type}.txt", "r")
        self.maxHealth = int(descriptorFile.readline())
        self.viewRange = int(descriptorFile.readline())
        self.shootRange = int(descriptorFile.readline())
        self.damage = int(descriptorFile.readline())
        self.maxAmmo = int(descriptorFile.readline())
        self.maxFuel = int(descriptorFile.readline())
        self.consumption = int(descriptorFile.readline())
        self.maxActionPoints = int(descriptorFile.readline())
        self.price = int(descriptorFile.readline())

        # im out of words....
        dummy1 = int(descriptorFile.readline())
        self.steppables = []
        for i in range(dummy1):
            dummy2 = descriptorFile.readline()
            self.steppables.append(dummy2[:-1])
        debug_print(self.state())
        descriptorFile.close()

    def state(self):
        return f"id:{self.id}, aps: {self.actionPoints} type:{self.type}, pos: {self.field.getPos()},\n\thealth: {self.health}, ammo: {self.ammo}, fuel:{self.fuel}"

    def astar(self, dest):
        
        debug_print(f"{self.id}, dest: {dest}")

        if(self.field.pos.dist(dest) > self.viewRange):
            # stappable check
            x = self.field.pos.x
            y = self.field.pos.y
            if(dest.x >= x and dest.y >= y):
                for f in self.seenFields:
                    if(f.pos.x == x+1 and f.pos.y == y+1):
                        debug_print(f"{self.id}: {self.field.pos}, bruteForce result: {str(f)}")
                        return f
            if(dest.x <= x and dest.y >= y):
                for f in self.seenFields:
                    if(f.pos.x == x-1 and f.pos.y == y+1):
                        debug_print(f"{self.id}: {self.field.pos}, bruteForce result: {str(f)}")
                        return f
            if(dest.x >= x and dest.y <= y):
                for f in self.seenFields:
                    if(f.pos.x == x+1 and f.pos.y == y-1):
                        debug_print(f"{self.id}: {self.field.pos}, bruteForce result: {str(f)}")
                        return f
            if(dest.x <= x and dest.y <= y):
                for f in self.seenFields:
                    if(f.pos.x == x-1 and f.pos.y == y-1):
                        debug_print(f"{self.id}: {self.field.pos}, bruteForce result: {str(f)}")
                        return f


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
            if(current.pos == dest):
                temp = current
                path.append(temp)
                while(temp.parent is not None):
                    path.append(temp.parent)
                    temp = temp.parent
                if(len(path) == 1):
                    return None
                debug_print(f"{self.id}: {self.field.pos}, astar path: {[str(field.pos) for field in path]}")
                return path[-2]

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
                    if(n in openSet):
                        if(tempG < n.g):
                            n.g = tempG
                            newPath = True
                    else:
                        n.g = tempG
                        newPath = True
                        openSet.append(n)
                    if(newPath):
                        n.h = max(abs(n.pos.x - dest.x), abs(n.pos.y - dest.y))
                        n.f = n.g + n.h
                        n.parent = current

    # nem is kell dummy move, ha van spread: ha nem látnak senki, spreadeljenek
    def dummyMove(self):
        debug_print(f"in dummy move {self.id}")
        neighbours = []
        for f in self.seenFields:
            if(abs(f.pos.x - self.field.pos.x) <= 1 and abs(f.pos.y - self.field.pos.y) <= 1
                    and f.pos not in [u.pos for u in self.seenUnits]):
                neighbours.append(f)
        for n in neighbours:
            if(n.type in self.steppables and n.pos != self.field.pos
                and f.pos not in [u.pos for u in self.seenUnits]):
                return n


    def __str__(self):
        return f"{self.id}, {str(self.field.pos)}"
