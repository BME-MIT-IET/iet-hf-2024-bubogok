from typing import List
from unit import Unit
from pos import Pos
import logger

import numpy as np
import logging
import itertools
import sys
import traceback
import os
from datetime import datetime


team = input()

if(os.path.isfile(f"{os.getcwd()}/python/logs/{team}leader.log")):
    os.remove(f"{os.getcwd()}/python/logs/{team}leader.log")
#leaderLogger = logging.basicConfig(filename=f"{os.getcwd()}/python/logs/{team}leader.log", encoding='utf-8', level=logging.DEBUG)
leaderLogger = logger.loggerMaker("leader", f"{os.getcwd()}/python/logs/{team}leader.log", )

def readUnits():
    units = []
    numma = int(input())
    leaderLogger.debug(f"in readunits, numma = {numma}")
    temp = input()
    for i in range(numma):
        leaderLogger.debug(f"writing for {i}. time")
        units.append(readUnitIn())
        temp = input()
    leaderLogger.debug(f"units read in")
    return units


def readUnitIn():    
    testID = int(input())
    testType = input()
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
    leaderLogger.debug(f"unit with id: {testID} was created and added for {testteam}")
    return Unit(testID, testType, testPosWType, testseenFields, testseenUnits, testseenControlPoints, testhealth, testammo, testfuel, testteam)


def main():
    leaderLogger.debug(f"log from:{datetime.now()}")
    try:
        while True:
            leaderLogger.debug(f"reading in")
            units = readUnits()
            #readUnitIn()
            leaderLogger.debug(f"past readunits")
            leaderLogger.debug(units)
            leaderLogger.debug(f"past unitwrite")
            units[0].dummyMove()
            units = readUnits()
            print("endTurn")
            leaderLogger.debug(f"FULL CIRCLE")
    except Exception as err:
        leaderLogger.debug(f"valami rák, {err=}, {type(err)=}")
        leaderLogger.debug(traceback.format_exception(err))
        #raise

main()