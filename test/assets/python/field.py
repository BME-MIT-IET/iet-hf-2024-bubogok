from pos import Pos
class Field:
    def __init__(self, params, parent=None):
        self.pos = Pos(params[:2])
        self.type = params[2]

        self.parent = parent
        self.g = 0
        self.h = 0
        self.f = 0
    
    def val(self):
        return self.pos.val()

    def getPos(self):
        return self.pos.val()

    def getType(self):
        return self.type

    def __eq__(self, other):
        return self.pos == other.pos