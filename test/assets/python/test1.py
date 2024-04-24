from unit import Unit
from pos import Pos
import logger
import inputHandler
from heuristic import heuristicAction
from dummy import dummyAction
from sarlMove import sarlMove, rlInit

import sys
import traceback
import os
from datetime import datetime

def communicate(units):
    for u in units:
        rlInit(units)
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
    units = []
    team = input()
    logger.debug_print(f"log from:{datetime.now()}")
    try:
        while True:
            phase = input()
            match phase:
                case "regPhase":
                    pass
                case "commPhase":
                    units = inputHandler.makeUnits()
                    print(communicate(units))
                case "endPhase":
                    endPhase(units)
    except Exception as err:
        logger.debug_print(f"valami rák, {err=}, {type(err)=}")
        for e in traceback.format_exception(err):
            logger.debug_print(e)
        logger.debug_print(traceback.format_exception(err))
        #raise

main()