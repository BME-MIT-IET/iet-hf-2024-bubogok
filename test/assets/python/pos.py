class Pos:
    def __init__(self, coords):
        self.x = coords[0]
        self.y = coords[1]
    
    def val(self):
        return self.x, self.y

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y