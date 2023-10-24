package temalab.test;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;

public final class Team {
	private Color color;
	private ArrayList<Vehicle> units;
	
	public Team(String name) {
		units = new ArrayList<Vehicle>();
		if(name == "green") {
			this.color = new Color(0, 1, 0, 1);
		} else if(name == "red") {
			this.color = new Color(1, 0, 0, 1);
		} else {
			this.color = new Color(.8f, .8f, .8f, 1);
		}
	}
	
	public Color getColor() {
		return this.color;
	}
	public void addUnit(Vehicle v) {
		this.units.add(v);
	}
	public ArrayList<Vehicle> units() {
		return this.units;
	}
	public ArrayList<Integer> unitIDs() {
		var ids = new ArrayList<Integer>();
		for(var u : units) {
			ids.add(u.getUUID());
		}
		return ids;
	}

	public Vehicle findByUUID(int id) {
		for(var u : units) {
			if(u.getUUID() == id) {
				return u;
			}
		}
		return null;
	}
	
	public void doAction(String[] answer) {
		Vehicle v = findByUUID(Integer.parseInt(answer[0]));
		Vector vec = new Vector(Integer.parseInt(answer[1]), Integer.parseInt(answer[2]));
		v.move(vec.x(), vec.y());
		System.out.println(vec.toString());
		
	}
}
