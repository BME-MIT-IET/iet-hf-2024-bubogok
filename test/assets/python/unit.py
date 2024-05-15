from pos import Pos
from controlPoint import ControlPoint
from unitView import UnitView
from field import Field
from logger import debug_print
# from priorityQueue import PriorityQueue

import time
import math
import os
from datetime import datetime
import matplotlib.pyplot as plt
import numpy as np
import random

nextTo = [-1, 1]

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

    def astar2(self, dest):
        debug_print(f" INITIAL CHECK {self.id}")
        if(dest in [u.pos for u in self.seenUnits] or self.field.pos == dest):
            dest.x = dest.x + random.choice(nextTo)
            dest.y = dest.y + random.choice(nextTo)
        frontier = list()
        frontier.append(self.field.pos)
        came_from = dict()
        came_from[self.field.pos] = None

        while(len(frontier) > 0):
            current = frontier.pop(0)
            if(current == dest):
                break
            # ----------------------------------------------------------
            neighbours = [] #stores positions of fields
            for f in self.seenFields:
                if(abs(f.pos.x - current.x) <= 1 
                    and abs(f.pos.y - current.y) <= 1
                    and f.pos not in [u.pos for u in self.seenUnits] 
                    and f.type in self.steppables):
                    neighbours.append(f.pos)
            # ----------------------------------------------------------
            # debug_print(f"neighbours: {[str(n) for n in neighbours]}")
            for n in neighbours:
                if(n not in came_from):
                    frontier.append(n)
                    came_from[n] = current
        current = dest
        path = []
        while current != self.field.pos:
            # debug_print(f"cf: {[str(p) for p in came_from]}")

            path.append(current)
            current = came_from[current]
        path.append(self.field.pos)
        debug_print(f"astar2 returning: {[str(n) for n in path]}")


        # maze = np.zeros((60, 60))
        # for p in path:
        #     maze[p.y][p.x] = 1
        # graph, (Q_plot1) = plt.subplots(1, 1)
        # Q_plot1.set_title(f"{self.id} a* way pos:{str(self.field.pos)}, dest: {dest}")
        # Q_plot1.imshow(maze, cmap='binary', interpolation='nearest')
        # plt.gca().invert_yaxis()
        # plt.show()



        if(len(path) < 2):
            return None
        return path[-2]




    def astar(self, dest):
        # this whole bruteForce part should be wwwaaayyy way way more sophisticated, but at this point im happy if it runs
        debug_print(f"{self.id} in astar, current is: {self.field.pos}, dest is: {dest}")
        debug_print(f"dist is : {self.field.pos.dist(dest)}, viewrange is : {self.viewRange}")

        if(self.field.pos.dist(dest) >= self.viewRange):
            debug_print(f"{self.id} in bruteforce, current is: {self.field.pos}, dest is: {dest}")
            x = self.field.pos.x
            y = self.field.pos.y

            neighbours = []
            for f in self.seenFields:
                if(abs(f.pos.x - self.field.pos.x) <= 1 and abs(f.pos.y - self.field.pos.y) <= 1
                    and f.pos not in [u.pos for u in self.seenUnits]):
                    neighbours.append(f.pos)

            if(dest.x >= x and dest.y >= y):
                for f in neighbours:
                    if(f.x == x+1 and f.y == y+1 and f.type in self.steppables):
                        debug_print(f"{self.id}: {self.field.pos}, bruteForce result: {str(f)}")
                        return f
                    else:
                        return self.dummyMove()
            if(dest.x <= x and dest.y >= y):
                for f in neighbours:
                    if(f.x == x-1 and f.y == y+1 and f.type in self.steppables):
                        debug_print(f"{self.id}: {self.field.pos}, bruteForce result: {str(f)}")
                        return f
                    else:
                        return self.dummyMove()
            if(dest.x >= x and dest.y <= y):
                for f in neighbours:
                    if(f.x == x+1 and f.y == y-1 and f.type in self.steppables):
                        debug_print(f"{self.id}: {self.field.pos}, bruteForce result: {str(f)}")
                        return f
                    else:
                        return self.dummyMove()
            if(dest.x <= x and dest.y <= y):
                for f in neighbours:
                    if(f.x == x-1 and f.y == y-1 and f.type in self.steppables):
                        debug_print(f"{self.id}: {self.field.pos}, bruteForce result: {str(f)}")
                        return f
                    else:
                        return self.dummyMove()

        return self.astar2(dest)

    # nem is kell dummy move, ha van spread: ha nem látnak senki, spreadeljenek
    def dummyMove(self):
        debug_print(f"bruteForceFailed, in dummy move {self.id}")
        neighbours = []
        for f in self.seenFields:
            if(abs(f.pos.x - self.field.pos.x) <= 1 and abs(f.pos.y - self.field.pos.y) <= 1
                    and f.pos not in [u.pos for u in self.seenUnits]):
                neighbours.append(f)
        for n in neighbours:
            if(n.type in self.steppables and n.pos != self.field.pos
                and f.pos not in [u.pos for u in self.seenUnits]):
                return n.pos


    def __str__(self):
        return f"{self.id}, {str(self.field.pos)}"