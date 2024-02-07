from pos import Pos
from controlPoint import ControlPoint
from unitView import UnitView
from field import Field

class Unit:
    def __init__(self, id, testPosWType, seenFields, seenUnits, seenControlPoints, health, ammo, fuel,  team):
        self.id = id
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

    def testWriteOut(self):
        self.pos.out()

    def dummyMove(self):
        neighbours = []
        for f in self.seenFields:
            if abs(f.pos.x - self.field.pos.x) <= 1 and abs(f.pos.y - self.field.pos.y) <= 1:
                neighbours.append(f)
        for n in neighbours:
            if n.type == "GRASS":
                print("move", self.id, n.pos.x, n.pos.y)
                break