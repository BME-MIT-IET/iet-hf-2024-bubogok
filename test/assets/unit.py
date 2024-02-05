from pos import Pos
from controlPoint import ControlPoint
from unitView import UnitView

class Unit:
    def __init__(self, id, pos, seenFields, seenUnits, seenControlPoints, health, ammo, fuel,  team):
        self.id = id
        self.pos = Pos(pos)
        self.health = health
        self.ammo = ammo
        self.fuel = fuel
        self.seenFields = []
        for f in seenFields:
            self.seenFields.append(Pos(f))

        self.seenUnits = []
        for u in seenUnits:
            self.seenUnits.append(UnitView(u))

        self.seenControlPoints = []
        for cp in seenControlPoints:
            self.seenControlPoints.append(ControlPoint(cp))
        self.team = team

    def testWriteOut(self):
        pass