from typing import List
from unit import Unit
from pos import Pos

import numpy as np
import itertools
import sys
import traceback
import os


logFile = open("log1.txt", "a")
logFile.truncate(0)

if(os.path.isfile("example.log")):
    os.remove("example.log")    

def readUnits():
    units = []
    numma = int(input())
    log("in readunits, numma = ", numma)
    temp = input()
    for i in range(numma):
        log("writing for", i, ". time")
        units.append(readUnitIn())
        temp = input()
    return units


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
    log("unit with id:", testID, "was created and added for", testteam)
    return Unit(testID, testPosWType, testseenFields, testseenUnits, testseenControlPoints, testhealth, testammo, testfuel, testteam)


def main():
    try:
        while True:
            units = readUnits()
            #readUnitIn()
            log("past readunits")
            log(units)
            log("past unitwrite")
            units[0].dummyMove()
            print("endTurn")
            log("FULL CIRCLE")
    except Exception as err:
        log(f"valami rák, {err=}, {type(err)=}")
        log(traceback.format_exception(err))
        #raise
    finally:
        logFile.close()

def log(*args, **kwargs):
    print(*args, **kwargs, file=logFile)


main()