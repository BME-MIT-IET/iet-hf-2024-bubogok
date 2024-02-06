from typing import List
from unit import Unit
from pos import Pos

import numpy as np
import itertools

units = []

logFile = open("log.txt", "a")
logFile.truncate(0)


def readUnits():
    numma = int(input())
    logFile.write("in readunits, numma = ", numma, "\n")
    temp = input()
    for i in range(numma):
        logFile.write("writing for", i, ". time")
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
    logFile.write("unit with id:", testID, "was created and added for", testteam)




readUnits()
#readUnitIn()
logFile.write("past readunits\n")
logFile.write(units)
logFile.write("past unitwrite\n")
logFile.close()
units[0].dummyMove()