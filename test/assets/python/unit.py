from pos import Pos
from controlPoint import ControlPoint
from unitView import UnitView
from field import Field
import logger

import logging
import time
import os
from datetime import datetime
  

files = os.listdir(f"{os.getcwd()}/python/logs")
for file in files:
    os.remove(f"{os.getcwd()}/python/logs/{file}")


runCounter = 0


class Unit:
    def __init__(self, id, testType, testPosWType, seenFields, seenUnits, seenControlPoints, health, ammo, fuel,  team):
        self.id = id
        self.type = testType
        self.field = Field(testPosWType)
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
        self.team = team
        descriptorFile = open(f"{os.getcwd()}/desciptors/{self.type}.txt")
        global unitLogger
        unitLogger = logger.loggerMaker("unit", f"{os.getcwd()}/python/logs/{self.id}{self.team}{self.type}.log")


    def testWriteOut(self):
        self.pos.out()


    def dummyMove(self):
        global runCounter
        runCounter += 1
        unitLogger.debug(f"starting------ {self.id}, {self.team}, {self.type}, RUN:{runCounter}")
        unitLogger.debug(f"current pos ={self.field.pos.val()}")
        neighbours = []
        for f in self.seenFields:
            #unitLogger.debug(f"f: {f.pos.val()}, self: {self.field.val()}")
            if abs(f.pos.x - self.field.pos.x) <= 1 and abs(f.pos.y - self.field.pos.y) <= 1:
                #unitLogger.debug("adding new neightbour")
                neighbours.append(f)
        unitLogger.debug(f"done interating over seenFields {len(neighbours)}")
        for n in neighbours:
            unitLogger.debug(f"current n to check:{n.val()}, {self.field.val()}, {n.type}")
            if n.type == "GRASS" and n.pos != self.field.pos:
                unitLogger.debug(f"{time.time() * 1000}")
                print("move", self.id, n.pos.x, n.pos.y)
                unitLogger.debug(f"sending this message: |move {self.id} {n.pos.x} {n.pos.y}|")
                break
        unitLogger.debug("end of iterating over neighbours")

    def heuristicMove(self):
        pass


