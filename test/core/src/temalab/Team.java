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
	
	public void doAction(String[] answer) {
		units.forEach((id, u) -> {
			u.updateWorld();
			int x = Map.instance().r.nextInt(3) - 1;
			int y = Map.instance().r.nextInt(3) - 1;
			u.move(u.pos().x() + x, u.pos().y() + y);
			//Map.instance().moveUnit(u, u.pos().x() + x, u.pos().y() + y);
		});
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
	
	public ArrayList<String> teamMembersToString(boolean toMonitor) {
		var res = new ArrayList<String>();
		units.forEach((id, u) -> {
			res.add(u.toString(toMonitor));
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
