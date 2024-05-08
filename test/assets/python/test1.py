from unit import Unit
from pos import Pos
import logger
import inputHandler
from heuristic import heuristicAction
from dummy import dummyAction
from sarlMove import sarlMove, rlInit
from teamLeader import tLAction

import sys
import traceback
import os
from datetime import datetime



def communicate(units):
    for u in units:
        tlInit(units)
        match sys.argv[1]:
            case "heuristic":
                result = heuristicAction(u)
            case "dummy":
                result = dummyAction(u)
            case "sarlMove":
                result = sarlMove(u)
        if(result is not None):
            return result
    return "endTurn"
        


# majd a revard functionon itt kell hogy számoljanak lhgy nyertunk vagy sem
def endPhase(units):
    result = input()
    logger.debug_print(result)


def main():
    sys.stdout = open(os.devnull, 'w')
    units = []
    teamCommands = list()
    team = input()
    logger.debug_print(f"log from:{datetime.now()}")
    try:
        while True:
            messageCounter = int(input())
            logger.setMessageCounter(messageCounter)
            phase = input()
            match phase:
                case "regPhase":
                    pass
                case "commPhase":
                    units = inputHandler.makeUnits()
                    if(len(teamCommands) == 0):
                        teamCommands += tLAction(units)
                    if(len(teamCommands) == 0):
                        teamCommands.append("endTurn")
                    logger.debug_print(f"teamCommands: {teamCommands}")
                    print(teamCommands.pop(0), file=sys.__stdout__, flush=True)
                    logger.debug_print(f"teamCommands: {teamCommands}")
                    sys.stdout = open(os.devnull, 'w')
                    # print(communicate(units))
                case "endPhase":
                    endPhase(units)
    except Exception as err:
        logger.debug_print(f"valami rák, {err=}, {type(err)=}")
        for e in traceback.format_exception(err):
            logger.debug_print(e)
        logger.debug_print(traceback.format_exception(err))
        #raise

main()