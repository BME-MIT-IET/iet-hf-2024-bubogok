from pos import Pos

class UnitView:
    def __init__(self, params):
        self.id = params[4]
        self.pos = Pos(params[:2])
        self.team = params[2]
        self.type = params[3]
