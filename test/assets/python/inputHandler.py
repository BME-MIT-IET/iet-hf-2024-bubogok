from unit import Unit
from logger import debug_print

# reads in a unit's data from stdin, return a list with the data
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
    debug_print(f"data from unit with id: {testID} was read in")
    return [testID, testType, testPosWType, testseenFields, testseenUnits, testseenControlPoints, testhealth, testammo, testfuel, testActionPoints, testteam]


# makes unit from the data got from readUnitIn function
def makeUnits(units):
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



# updates an existing unit, based on the readUnitIn function
def updateUnits(units):
    numma = int(input())
    debug_print(f"updating {numma} units")
    starterBrakets = input()
    for i in range(numma):
        # TODO: this is still fucking ungly
        data = readUnitIn()
        updateID = int(data[0])
        for u in units:
            if u.id == updateID:
                u.update(data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9])
        separatinCommaOrEndingBracket = input()
    debug_print(f"units updated")
    return units