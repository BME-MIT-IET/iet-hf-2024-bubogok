package temalab;

import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public final class Team {
	private Color color;
	private String name;
	private final HashMap<Integer, Unit> units;
	
	public Team(String name) {
		units = new HashMap<Integer, Unit>();
		if(name == "white") {
			this.color = new Color(1, 1, 1, 1);
		} else if(name == "red") {
			this.color = new Color(1, 0, 0, 1);
		} else {
			this.color = new Color(0f, 0f, 0f, 1);
		}
		this.name = name;
	}
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		var renderUnits = new HashMap<Integer, Unit>();
		renderUnits.putAll(units);
		renderUnits.forEach((id, u) -> {
			u.render(sr, sb, color);
		});
	}
	public Color getColor() {
		return color;
	}
	public String getName() {
		return name;
	}
	public void addUnit(Unit v) {
		this.units.put(v.getUUID(), v);
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

	public ArrayList<UnitView> requestUnitViews(Position pos, float size) {
		var view = new ArrayList<UnitView>();
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
			int x = Simu.r.nextInt(3) - 1;
			int y = Simu.r.nextInt(3) - 1;
			u.move(u.pos().x() + x, u.pos().y() + y);
		});
	}

	public void makeShot(int damage, Position p) {
		Unit asdf = null;
		for(var u : units.values()) {
			if(u.pos.equals(p)) {
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
			res.add(u.toString());
		});
		return res;
	}
}
