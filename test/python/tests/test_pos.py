from unittest import TestCase, main

from pos import Pos

class PosTest(TestCase):
    def test_dist(self):
        result = Pos([-1, 2]).dist(Pos([3, 5]))
        assert(result == 4)

    def test_euclDist(self):
        result = Pos([-1, 2]).euclDist(Pos([3, 5]))
        assert(result == 5)

if __name__ == "__main__":
    _ = main()

