package temalab.test;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public final class Team {
	private Color color;
	//TODO: át kellene álni hashmapra
	private HashMap<Integer, Vehicle> units;
	//private ArrayList<Vehicle> units;
	
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
	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
		for(Vehicle u : units) {
			u.render(sr, sb, bf, color);				
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
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(Vehicle u : units) {
			ids.add(u.getUUID());
		}
		return ids;
	}
	//Hashmap esetén megszűnik
	public Vehicle findByUUID(int id) {
		for(Vehicle u : units) {
			if(u.getUUID() == id) {
				return u;
			}
		}
		return null;
	}
	//TODO: ki kellene javítani, ez így végtelen bohóckodás
	public void doAction(String[] answer) {
		Vehicle v = findByUUID(Integer.parseInt(answer[0]));
		Vector vec = new Vector(Integer.parseInt(answer[1]), Integer.parseInt(answer[2]));
		v.move(vec.x(), vec.y());
	}
}
