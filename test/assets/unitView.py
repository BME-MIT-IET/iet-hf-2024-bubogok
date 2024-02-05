from pos import Pos

class UnitView:
    def __init__(self, params):
        self.pos = Pos(params[:2])
        self.team = params[2]
