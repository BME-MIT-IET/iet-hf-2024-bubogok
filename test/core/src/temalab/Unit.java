package temalab;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public abstract class Unit {
	protected final int ID;
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
	protected int maxActionPoints;
	protected int actionPoints;
	protected Position shootingPos;

	protected boolean currentlyShooting;

	public Unit(Position pos, Team t) {
		seenFields = new ArrayList<Field>();
		seenUnits = new ArrayList<UnitView>();
		ID = Simu.r.nextInt(1000000);
		this.pos = pos;
		team = t;
		currentlyShooting = false;
		shootingPos = null;
	}

	public void render(ShapeRenderer sr, SpriteBatch sb, Color c) {
		float size = Map.instance().squareSize();
		Vector2 center = pos.screenCoords();
		
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(c);
		sr.circle(center.x, center.y, size / 2);
		sr.end();
		
		sb.begin();
		sb.draw(getTexture(), center.x - (size / 2), center.y - (size / 2), size, size);
		sb.end();
		
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setColor(c);
		sr.circle(center.x, center.y, Map.instance().universalDistanceConstant() * size * shootRange);
		sr.circle(center.x, center.y, Map.instance().universalDistanceConstant() * size * viewRange);
		sr.end();
		if(currentlyShooting) {
			sr.begin(ShapeRenderer.ShapeType.Line);
			sr.setColor(c);
			sr.line(center, shootingPos.screenCoords());
			sr.end();
			currentlyShooting = false;
		}
	}

	public void move(int x, int y) {
		if(fuel - consumption > 0 && actionPoints > 0) {
			if(Map.instance().validateMove(steppableTypes, pos, new Position(x, y))) {
            	pos = new Position(x, y);
				fuel -= consumption;
				actionPoints--;
        	}
		}
	}

	public void shoot(Position p) {
		if (ammo > 0 && actionPoints > 0) {
			if (pos.inDistance(p, shootRange + 0.5f)) {
				Map.instance().makeShot(damage, p);
			}
			shootingPos = p;
			currentlyShooting = true;
			ammo--;
			actionPoints--;
		}
	}

	public abstract Texture getTexture();

	public void updateWorld() {
		actionPoints = maxActionPoints;
		seenFields = Map.instance().requestFileds(pos, viewRange + 0.5f);
		seenUnits = Map.instance().requestUnitViews(pos, viewRange + 0.5f);
		seenControlPoints = Map.instance().requestControlPoints(pos, viewRange + 0.5f);
		Position enemyPosition = null;
		for(var u : seenUnits) {
			if(u.team != team) {
				enemyPosition = new Position(u.pos);
			}
		}
		if(enemyPosition != null) {
			shoot(enemyPosition);
		}
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

	public abstract UnitView getView();


	public void takeShot(int recievedDamage) {	
		health -= recievedDamage;
		if(health <= 0) {
			team.unitDied(ID);
		}
	}

	@Override
	public String toString() {
		return ID + "\n"
		+ pos.toString() + "\n"
	 	+ seenFields.toString() + "\n"
		+ seenUnits.toString() + "\n"
		+ health + "\n"
		+ ammo + "\n"
		+ fuel + "\n"
		+ team.getName();
	}
}