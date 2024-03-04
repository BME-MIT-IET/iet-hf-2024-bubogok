from typing import List
from unit import Unit
from pos import Pos
from logger import *

import sys
import traceback
import os
from datetime import datetime

team = input()

units = []

def readUnits():
    debug_print("asdf")
    numma = input()
    debug_print(numma)
    numma = int(numma)
    ##numma = int(input())
    debug_print(f"in readunits, numma = {numma}")
    temp = input()
    for i in range(numma):
        debug_print(f"writing for {i}. time")
        units.append(readUnitIn())
        temp = input()
    debug_print(f"units read in")


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
    debug_print(f"unit with id: {testID} was created and added for {testteam}")
    return Unit(testID, testType, testPosWType, testseenFields, testseenUnits, testseenControlPoints, testhealth, testammo, testfuel, testteam)

def updateUnits():
    numma = int(input())
    debug_print(f"in updateUnits, numma = {numma}")
    temp = input()
    for i in range(numma):
        debug_print(f"writing for {i}. time")
        updateID = int(input())
        for u in units:
            if u.id == updateID:
                u.update()

        temp = input()
    debug_print(f"units updated")

def noop():
    pass

def main():
    print("hello from python", file=sys.stderr)
    debug_print(f"log from:{datetime.now()}")
    try:
        debug_print(f"reading in")
        readUnits()
        debug_print(f"past readunits")
        debug_print(units)
        debug_print(f"past unitwrite")
        print("endTurn")
        while True:
            updateUnits()
            units[0].dummyMove()
            updateUnits()
            print("endTurn")
            debug_print(f"FULL CIRCLE")
    except Exception as err:
        debug_print(f"valami rák, {err=}, {type(err)=}")
        debug_print(traceback.format_exception(err))
        #raise



# def tracefunc(frame, event, arg, indent=[0]):
#       if event == "call":
#           indent[0] += 2
#           print("-" * indent[0] + "> call function", frame.f_code.co_name, file=sys.stderr)
#       elif event == "return":
#           print("<" + "-" * indent[0], "exit function", frame.f_code.co_name, file=sys.stderr)
#           indent[0] -= 2
#       return tracefunc

# sys.setprofile(tracefunc)

# main()
    

# tracer = trace.Trace(ignoredirs=[sys.prefix, sys.exec_prefix], trace=0, count=0, countfuncs=1)
# tracer.run('main()')



    
# sys.settrace(debug_print)
# sys.setprofile('call')
# sys.call_tracing(main, ())

main()