import sys

def debug_print(*args):
    print(*args, file=sys.stderr)