from unit import Unit
from unitView import UnitView
from pos import Pos
from logger import debug_print


def dummyAction(unit):
	debug_print(unit.state())
	if(unit.actionPoints == 0):
		return None
	if(unit.seenUnits is not None and unit.ammo > 0):
		for u in unit.seenUnits:
			if(unit.team != u.team):
				dist = math.sqrt((unit.fields.pos.x - u.pos.x)**2 + (unit.field.pos.y - u.pos.y)**2)
				if(dist <= unit.shootRange):
					return f"shoot {unit.id} {u.pos.x} {u.pos.y}"
				else:
					return None

	if(unit.fuel - unit.consumption > 0):
		neighbours = []
		for f in unit.seenFields:
			if(abs(f.pos.x - unit.field.pos.x) <= 1 and abs(f.pos.y - unit.field.pos.y) <= 1
					and f.pos not in [u.pos for u in unit.seenUnits]):
				neighbours.append(f)
		for n in neighbours:
			if(n.type in unit.steppables and n.pos != unit.field.pos
					and f.pos not in [u.pos for u in unit.seenUnits]):
				return f"move {unit.id} {n.pos.x} {n.pos.y}"
	return None