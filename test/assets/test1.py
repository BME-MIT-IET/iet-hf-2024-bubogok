from typing import List
from unit import Unit
from pos import Pos

import numpy as np
import itertools
'''
for i in range(3):
    with open("asdf.txt", "w") as f:
        line = input()
        line = line.replace(";", "")
        f.write(line)
'''

def readUnitIn():    
    testID = int(input())
    testPos = list(map(int, input().split()))

    #fields
    xd = input()
    pairs = xd[1:-1].split(",")
    testseenFields = [[int(num) for num in pair.strip().split()] for pair in pairs]
    #/fields

    #seenUnits
    xd = input()


    tris = xd.replace("[", "").replace("]", "").replace(";", "")
    sublists = tris.split(",")
    testseenUnits = []
    for sublist in sublists:
        sublist = sublist.strip()
        elements = sublist.split(" ")
    testseenUnits.append([int(elements[0]), int(elements[1]), elements[3].strip()])
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
    global testUnit
    testUnit = Unit(testID, testPos, testseenFields, testseenUnits, testseenControlPoints, testhealth, testammo, testfuel, testteam)


def main():
    print("asdf")
    readUnitIn()
    testUnit.testWriteOut()
    print("BYE")

if __name__ == "__main__":
    main()