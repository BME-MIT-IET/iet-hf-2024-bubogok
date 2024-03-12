import unittest
from unittest.mock import patch


from controlPoint import ControlPoint
from field import *
from logger import *
from pos import *
from test1 import readUnits, readUnitIn, updateUnits
from unit import *
from unitView import *



class TestUnitReadIn(unittest.TestCase):
    def setUp(self):
        f = open("testOut.txt", "r")
        global content
        content = f.readlines()

        f.close()
        

    def test_unti_read_in(self):
        testID = content[3]
        testType = content[4]
        testPosRaw = content[5]
        testPosWType = [int(testPosRaw[0]), int(testPosRaw[1]), testPosRaw[2].strip()]

        #fields
        xd = content[6]
        tris = xd.replace("[", "").replace("]", "")
        sublists = tris.split(",")
        testseenFields = []
        for sublist in sublists:
            sublist = sublist.strip()
            elements = sublist.split(" ")
            testseenFields.append([int(elements[0]), int(elements[1]), elements[2].strip()])
        #/fields


        #seenUnits
        xd = input()
        tris = xd.replace("[", "").replace("]", "")
        sublists = tris.split(",")
        testseenUnits = []
        for sublist in sublists:
            sublist = sublist.strip()
            elements = sublist.split(" ")
            testseenUnits.append([int(elements[0]), int(elements[1]), elements[2].strip(), elements[3].strip()])
        #/seenUnits

        
        #controlPoints
        xd = input()
        xd = xd[1:-1]
        lists = xd.split(",")
        lists = [list_item.strip() for list_item in lists]
        testseenControlPoints = []
        for list_item in lists:
            numbers = [int(num) for num in list_item.split()]
            testseenControlPoints.append(numbers)
        #/controlPoints


        testhealth = int(input())
        testammo = int(input())
        testfuel = int(input())
        testActionPoints = int(input())
        testteam = input()

        


if __name__ == '__main__':
    unittest.main()
