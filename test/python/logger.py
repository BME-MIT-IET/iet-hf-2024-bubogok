import sys

msgCounter = -1

def debug_print(*args):
    print(msgCounter, *args, file=sys.stderr)


def setMessageCounter(msgC):
    global msgCounter
    msgCounter = msgC
