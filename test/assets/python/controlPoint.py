from pos import Pos

class ControlPoint:
    def __init__(self, params):
        self.pos = Pos(params[:2])
        self.percentage = params[2]
        self.size = params[3]