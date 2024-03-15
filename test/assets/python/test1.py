from unit import Unit
from pos import Pos
import logger
import inputHandler

import sys
import traceback
import os
from datetime import datetime

def communicate(units):
    result = units[0].heuristicAction()
    if(result is None):
        result = "endTurn"
    logger.debug_print(result)
    return result


# majd a revard functionon itt kell hogy számoljanak lhgy nyertunk vagy sem
def endPhase():
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
                    endPhase()
    except Exception as err:
        logger.debug_print(f"valami rák, {err=}, {type(err)=}")
        for e in traceback.format_exception(err):
            logger.debug_print(e)
        logger.debug_print(traceback.format_exception(err))
        #raise

main()