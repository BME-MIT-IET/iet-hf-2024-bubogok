from pos import Pos
from controlPoint import ControlPoint
from unitView import UnitView
from field import Field

import logging
import time
import os


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
        logging.basicConfig(filename=f"{os.getcwd()}/python/logs/example.log", encoding='utf-8', level=logging.DEBUG)

    def testWriteOut(self):
        self.pos.out()

    def dummyMove(self):
        logging.debug(f"starting------ {self.id}, {self.team}, {self.type}")
        logging.debug(f"current pos ={self.field.pos.val()}")
        neighbours = []
        for f in self.seenFields:
            #logging.debug(f"f: {f.pos.val()}, self: {self.field.val()}")
            if abs(f.pos.x - self.field.pos.x) <= 1 and abs(f.pos.y - self.field.pos.y) <= 1:
                #logging.debug("adding new neightbour")
                neighbours.append(f)
        logging.debug(f"done interating over seenFields {len(neighbours)}")
        for n in neighbours:
            logging.debug(f"current n to check:{n.val()}, {self.field.val()}, {n.type}")
            if n.type == "GRASS" and n.pos != self.field.pos:
                logging.debug(f"{time.time() * 1000}")
                print("move", self.id, n.pos.x, n.pos.y)
                logging.debug(f"sending this message: |move {self.id} {n.pos.x} {n.pos.y}|")
                break
        logging.debug("end of iterating over neighbours")