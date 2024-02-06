from typing import List
from unit import Unit
from pos import Pos

import numpy as np
import itertools


units = []

def readUnits():
    numma = int(input())
    temp = input()
    for i in range(numma):
        readUnitIn()
        temp = input()


def readUnitIn():    
    testID = int(input())
    testPosRaw = input().split(" ")
    testPosWType = [int(testPosRaw[0]), int(testPosRaw[1]), testPosRaw[2].strip()]

    #fields
    xd = input()
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
    units.append(Unit(testID, testPosWType, testseenFields, testseenUnits, testseenControlPoints, testhealth, testammo, testfuel, testteam))

def main():
    print("asdf")

    readUnits()

    print("BYE")
    print(units)

if __name__ == "__main__":
    main()