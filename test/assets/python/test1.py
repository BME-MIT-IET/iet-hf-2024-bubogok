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
        units[0].dummyMove()
        logger.debug_print(units)
        print("endTurn")
        while True:
            units = inputHandler.updateUnits(units)
            units[0].dummyMove()
            units = inputHandler.updateUnits(units)
            print("endTurn")
            logger.debug_print(f"FULL CIRCLE")
    except Exception as err:
        logger.debug_print(f"valami rák, {err=}, {type(err)=}")
        logger.debug_print(traceback.format_exception(err))
        #raise

main()