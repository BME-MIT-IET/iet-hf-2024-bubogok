from unit import Unit
from pos import Pos
import logger
import inputHandler

import sys
import traceback
import os
from datetime import datetime

def communicate(units):
    result = "endTurn"


    return result


def main():
    units = []
    team = input()
    logger.debug_print(f"log from:{datetime.now()}")
    try:
        while True:
            units = inputHandler.makeUnits()
            print(communicate(units))
    except Exception as err:
        logger.debug_print(f"valami rák, {err=}, {type(err)=}")
        for e in traceback.format_exception(err):
            logger.debug_print(e)
        # logger.debug_print(traceback.format_exception(err))
        #raise

main()