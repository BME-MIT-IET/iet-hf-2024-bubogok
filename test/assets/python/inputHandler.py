from unit import Unit
from logger import debug_print


# reads in a unit's data from stdin, return a list with the data
def readUnitIn():    
    testID = int(input())
    testType = input()
    testPosRaw = input().split(" ")
    testPosWType = [int(testPosRaw[0]), int(testPosRaw[1]), testPosRaw[2].strip()]

    #fields
    fieldInput = input()
    fieldtris = fieldInput.replace("[", "").replace("]", "")
    fieldsublists = fieldtris.split(",")
    testseenFields = []
    for sublist in fieldsublists:
        sublist = sublist.strip()
        elements = sublist.split(" ")
        testseenFields.append([int(elements[0]), int(elements[1]), elements[2].strip()])
    #/fields


    #seenUnits
    unitsInput = input()
    unitTris = unitsInput.replace("[", "").replace("]", "")
    testseenUnits = []
    if(len(unitTris) != 0):
        unitsublists = unitTris.split(",")
        for sublist in unitsublists:
            sublist = sublist.strip()
            elements = sublist.split(" ")
            testseenUnits.append([int(elements[0]), int(elements[1]), elements[2].strip(), elements[3].strip(), int(elements[4].strip())])
        #/seenUnits

    
    #controlPoints
    testseenControlPoints = []
    cpInput = input()
    cpInput = cpInput[1:-1]
    if(len(cpInput) != 0):
        lists = cpInput.split(",")
        lists = [list_item.strip() for list_item in lists]
        for list_item in lists:
            numbers = [int(num) for num in list_item.split()]
            testseenControlPoints.append(numbers)
    #/controlPoints


    testhealth = int(input())
    testammo = int(input())
    testfuel = int(input())
    testActionPoints = int(input())
    testteam = input()
    debug_print(f"data from unit with id: {testID} was read in")
    return [testID, testType, testPosWType, testseenFields, testseenUnits, testseenControlPoints, testhealth, testammo, testfuel, testActionPoints, testteam]


# makes unit from the data got from readUnitIn function
def makeUnits():
    units = []
    numma = int(input())
    debug_print(f"making {numma} units")
    starterBrakets = input()
    for i in range(numma):
        # TODO: this is fucking ungly
        data = readUnitIn()
        units.append(Unit(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10]))
        separatinCommaOrEndingBracket = input()
    debug_print(f"units made")
    return units