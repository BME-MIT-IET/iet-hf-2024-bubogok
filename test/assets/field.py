from pos import Pos
class Field:
    def __init__(self, params):
        self.pos = Pos(params[:2])
        self.type = params[2]
    
    def val(self):
        return self.pos.val()