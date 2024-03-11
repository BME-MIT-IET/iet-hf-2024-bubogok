from unit import Unit
from pos import Pos
import logger
import inputHandler

import sys
import traceback
import os
from datetime import datetime



def main():
    units = []
    team = input()
    logger.debug_print(f"log from:{datetime.now()}")
    try:
        # TODO:rohadtul nem így kellene csinálni ezt
        units = inputHandler.makeUnits(units)
        units[0].heuristicAction()
        units = inputHandler.updateUnits(units)
        print("endTurn")
        while True:
            units = inputHandler.updateUnits(units)
            units[0].heuristicAction()
            units = inputHandler.updateUnits(units)
            print("endTurn")
            logger.debug_print(f"FULL CIRCLE")
    except Exception as err:
        logger.debug_print(f"valami rák, {err=}, {type(err)=}")
        for e in traceback.format_exception(err):
            logger.debug_print(e)
        # logger.debug_print(traceback.format_exception(err))
        #raise

main()