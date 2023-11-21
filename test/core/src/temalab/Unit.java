package temalab;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public abstract class Unit {
	protected int ID;
	protected Position pos;
	protected ArrayList<Field> seenFields;
	protected ArrayList<UnitView> seenUnits;
	protected ArrayList<ControlPoint> seenControlPoints;
	protected Team team;
	protected ArrayList<Field.Type> steppableTypes;

	protected int health;
	protected int maxHealth;
	protected int viewRange;
	protected int shootRange;
	protected int damage;
	protected int ammo;
	protected int maxAmmo;
	protected int fuel;
	protected int maxFuel;
	protected int consumption;
	protected int price;
	protected int actionPoints;

	public Unit(Position pos, Team t) {
		seenFields = new ArrayList<Field>();
		seenUnits = new ArrayList<UnitView>();
		ID = Test.r.nextInt(1000000);
		this.pos = pos;
		team = t;
	}

	public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf, Color c) {
		float size = Map.instance().squareSize();
		Vector2 center = pos.screenCoords();

		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(c);
		sr.circle(center.x, center.y, size / 2);
		sr.end();

		sb.begin();
		sb.draw(getTexture(), center.x - (size / 2), center.y - (size / 2), size, size);
		sb.end();
	}

	public void move(int x, int y) {
		if(fuel > 0 && actionPoints > 0) {
			if(Map.instance().validateMove(steppableTypes, pos, new Position(x, y))) {
            	pos = new Position(x, y);
				fuel -= consumption;
				actionPoints--;
        	}
		}
	}

	public void shoot(int x, int y) {
		if (ammo > 0 && actionPoints > 0) {
			if (pos.inDistance(new Position(x, y), shootRange + 0.5f)) {
				Map.instance().makeShot(damage, x, y);
			}
			ammo--;
			actionPoints--;
		}

	}

	public abstract Texture getTexture();

	public void updateWorld() {
		seenFields = Map.instance().requestFileds(pos, viewRange + 0.5f);
		seenUnits = Map.instance().requestUnitViews(pos, viewRange + 0.5f);
		seenControlPoints = Map.instance().requestControlPoints(pos, viewRange + 0.5f);
	}

	public void updateSelf(int percentage) {
		if(health < maxHealth) {
			health +=  maxHealth / percentage;
		}
		if(ammo < maxAmmo) {
			ammo +=  maxAmmo / percentage;
		}
		if(fuel < maxFuel) {
			fuel +=  maxFuel / percentage;
		}
	}

	public int getUUID() {
		return this.ID;
	}

	public Position pos() {
		return pos;
	}

	public Team team() {
		return team;
	}

	public void takeShot(int recievedDamage) {
		health -= recievedDamage;
		if(health > 0) {
			team.unitDied(ID);
		}
	}

	public abstract UnitView getView();

	@Override
	public String toString() {
		return ID + "; pos: "
		+ pos.toString()
		/*
		+ "; seenFields: " + seenFields.toString()
		*/
		+ "; seenUnits: " + seenUnits.toString()
		+ health + ';' + maxHealth + ';'
		+ viewRange + ';' + shootRange + ';'
		+ damage + ';' + ammo + ';' + maxAmmo + ';'
		+ fuel + ';' + maxFuel + ';' + consumption + ';'
		+ price + ';' + actionPoints + ';'

		+ team.getColor().toString() + ';';
	}
}