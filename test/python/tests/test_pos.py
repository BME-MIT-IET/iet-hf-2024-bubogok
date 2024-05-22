from unittest import TestCase, main

from pos import Pos

class PosTest(TestCase):
    def setUp(self):
        self.pos1 = Pos([-1, 2])
        self.pos2 = Pos([3, 5])

    def test_dist(self):
        result = self.pos1.dist(self.pos2)
        assert(result == 4)

    def test_euclDist(self):
        result = self.pos1.euclDist(self.pos2)
        assert(result == 5)

if __name__ == "__main__":
    _ = main()

