import logging


formatter = logging.Formatter("%(asctime)s %(levelname)s %(message)s")

def loggerMaker(name, file, level=logging.DEBUG):
    handler = logging.FileHandler(file)        
    handler.setFormatter(formatter)

    logger = logging.getLogger(name)
    logger.setLevel(level)
    logger.addHandler(handler)

    return logger