package temalab;

import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.graphics.Color;

public final class Team {
	private Color color;
	private String name;
	private final HashMap<Integer, Unit> units;
	private int budget = 0;
	
	public Team(String name, int budget) {
		units = new HashMap<Integer, Unit>();
		if(name == "white") {
			this.color = new Color(1, 1, 1, 1);
		} else if(name == "red") {
			this.color = new Color(1, 0, 0, 1);
		} else {
			this.color = new Color(0f, 0f, 0f, 1);
		}
		this.name = name;
		this.budget = budget;
	}

	public Color getColor() {
		return color;
	}
	public String getName() {
		return name;
	}
	public void addUnit(Unit v) {
		int currentBalance = 0;
		for (java.util.Map.Entry<Integer, Unit> entry : units.entrySet()) {
			currentBalance += entry.getValue().price();
        }
		if(currentBalance + v.price() <= budget) {
			this.units.put(v.getUUID(), v);
		}
	}

	public HashMap<Integer, Unit> units() {
		return this.units;
	}
	
	public ArrayList<Integer> unitIDs() {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		units.forEach((id, u) -> {
			ids.add(u.getUUID());
		});
		return ids;
	}

	public ArrayList<Unit> requestUnits(Position pos, float size) {
		var view = new ArrayList<Unit>();
		units.forEach((id, u) -> {
			if(pos.inDistance(u.pos(), size) && u.pos() != pos) {
				view.add(u);
			}
		});
		return view;
	}

	public ArrayList<PerceivedUnit> requestUnitViews(Position pos, float size) {
		var view = new ArrayList<PerceivedUnit>();
		units.forEach((id, u) -> {
			if(pos.inDistance(u.pos(), size) && u.pos() != pos) {
				view.add(u.getView());
			}
		});
		return view;
	}
	
	public void moveUnit(int id, Position newPos) {
			//TODO: kitalálni, hogy ez hogy lesz
			// int x = Map.instance().r.nextInt(3) - 1;
			// int y = Map.instance().r.nextInt(3) - 1;
			// var newPos = new Position(u.pos().x() + x, u.pos().y() + y);
			var newField = Map.instance().getField(newPos);
			var u = units.get(id);
			if(newField == null || u == null) {
				return; //TODO: jobb hibauzenet
			}
			u.move(newField);
	}

	public void updateUnits() {
		units.forEach((id, u) -> {
			u.updateWorld();
		});
	}

	public void refillActionPoints() {
		units.forEach((id, u) -> {
			u.refillActionPoints();
		});
	}

	public void fireUnit(int id, Position newPos) {
			//TODO: kitalálni, hogy ez hogy lesz
			// int x = Map.instance().r.nextInt(3) - 1;
			// int y = Map.instance().r.nextInt(3) - 1;
			// var newPos = new Position(u.pos().x() + x, u.pos().y() + y);
			var newField = Map.instance().getField(newPos);
			var u = units.get(id);
			if(newField == null || u == null) {
				return; //TODO: jobb hibauzenet
			}
			u.shoot(newField);
	}

	public void makeShot(int damage, Position p) {
		Unit asdf = null;
		for(var u : units.values()) {
			if(u.pos().equals(p)) {
				asdf  = u;
			}
		}
		if(asdf != null) {
			asdf.takeShot(damage);
		}
	}

	public void unitDied(int id) {
		units.remove(id);
	}
	
	public ArrayList<String> teamMembersToString() {
		var res = new ArrayList<String>();
		units.forEach((id, u) -> {
			res.add(u.toString(false));
		});
		return res;
	}

	public ArrayList<String> toMonitor() {
		var res = new ArrayList<String>();
		res.add(name);
		units.forEach((id, u) -> {
			res.add(u.toString(true));
		});
		return res;
	}
}
