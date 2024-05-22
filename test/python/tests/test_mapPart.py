from unittest import TestCase, main

from mapPart import MapPart
from unitView import UnitView

class UnitViewMock(UnitView):
    def __init__(self, team, health):
        self.team= team
        self.health = health

class MapPartTest(TestCase):
    def setUp(self):
        self.mapPart = MapPart(0, 0);

    def test_getTeamScore(self):
        self.mapPart.addUnit(UnitViewMock(team="red", health=70))
        self.mapPart.addUnit(UnitViewMock(team="red", health=30))
        self.mapPart.addUnit(UnitViewMock(team="green", health=40))
        self.mapPart.addUnit(UnitViewMock(team="blue", health=10))

        result = self.mapPart.getTeamScore("red")
        assert(result == 50)

    def test_getTeamScore_noUnits(self):
        result = self.mapPart.getTeamScore("red")
        assert(result == 0)

if __name__ == "__main__":
    _ = main()

