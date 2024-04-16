from unit import Unit
from unitView import UnitView
from pos import Pos
from logger import debug_print

import math


def dummyMove(unit):
    debug_print(f"heuristicAction, for id: {unit.id}")
    neighbours = []
    for f in unit.seenFields:
        if(abs(f.pos.x - unit.field.pos.x) <= 1 and abs(f.pos.y - unit.field.pos.y) <= 1
                and f.pos not in [u.pos for u in unit.seenUnits]):
            neighbours.append(f)
    for n in neighbours:
        if(n.type in unit.steppables and n.pos != unit.field.pos
            and f.pos not in [u.pos for u in unit.seenUnits]):
            return f"move {unit.id} {n.pos.x} {n.pos.y}"

def heuristicAction(unit):
    debug_print(unit.state())
    if(unit.actionPoints == 0):
        return None
    if(unit.seenUnits is not None and unit.ammo > 0):
        for u in unit.seenUnits:
            if(unit.team != u.team):
                dist = math.sqrt((unit.field.pos.x - u.pos.x)**2 + (unit.field.pos.y - u.pos.y)**2)
                if(dist <= unit.shootRange):
                    return f"shoot {unit.id} {u.pos.x} {u.pos.y}"
                else:
                    break
                    return None #kilép az egészből, így a mozgás nem is lesz checkelev

    if(unit.fuel - unit.consumption > 0):
        if(len(unit.seenControlPoints) != 0):
            path = astar(unit, unit.seenControlPoints[0])
            if(path is None): #astar nem látja a dest-et
                # nem így!!!
                return None
            elif(len(path) == 1): # már a destben vagyunk
                return None
            else:
                return f"move {unit.id} {path[-2].pos.x} {path[-2].pos.y}"
        else:
            return dummyMove(unit)
    return None


def astar(unit, dest):
    closedSet = []
    openSet = []
    path = []
    openSet.append(unit.field)
    while(len(openSet) > 0):
        winner = openSet[0]
        for f in openSet:
            if(f.f < winner.f):
                winner = f

        current = winner
        if(current.pos == dest.pos):
            temp = current
            path.append(temp)
            while(temp.parent is not None):
                path.append(temp.parent)
                temp = temp.parent
            return path

        openSet.remove(current)
        closedSet.append(current)

        neighbours = []
        for f in unit.seenFields:
            if(abs(f.pos.x - current.pos.x) <= 1 and abs(f.pos.y - current.pos.y) <= 1
                and f.pos not in [u.pos for u in unit.seenUnits]):
                neighbours.append(f)

        for n in neighbours:
            if(not n in closedSet and n.type in unit.steppables):
                tempG = current.g + max(abs(n.pos.x - current.pos.x), abs(n.pos.y - current.pos.y))
                newPath = False
                if n in openSet:
                    if(tempG < n.g):
                        n.g = tempG
                        newPath = True
                else:
                    n.g = tempG
                    newPath = True
                    openSet.append(n)
                if(newPath):
                    n.h = max(abs(n.pos.x - dest.pos.x), abs(n.pos.y - dest.pos.y))
                    n.f = n.g + n.h
                    n.parent = current
            
