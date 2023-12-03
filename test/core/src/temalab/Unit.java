package temalab;

import java.util.ArrayList;

public abstract class Unit {
	protected final int ID;
	protected Position pos;
	protected ArrayList<Field> seenFields;
	protected ArrayList<PerceivedUnit> seenUnits;
	protected ArrayList<ControlPoint> seenControlPoints;
	protected Team team;
	protected ArrayList<Field.Type> steppableTypes;

	protected UnitView listener;

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
	

	public Unit(Position pos, Team t) {
		seenFields = new ArrayList<Field>();
		seenUnits = new ArrayList<PerceivedUnit>();
		ID = Map.instance().r.nextInt(1000000);
		this.pos = pos;
		team = t;
		shootingPos = null;
		listener = null;
	}

	public void move(int x, int y) {
		if(fuel - consumption >= 0 && actionPoints > 0) {
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
			if(listener != null) {
				listener.onShootStart(p);
			}
			shootingPos = p;
			ammo--;
			actionPoints--;
		}
	}

	public void takeShot(int recievedDamage) {	
		health -= recievedDamage;
		if(health <= 0) {
			team.unitDied(ID);
		}
	}
	
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
		int updateAmount;
		if(health <= maxHealth) {
			updateAmount = (int)Math.ceil(maxHealth * (percentage/100));
			
			health = Math.min(maxHealth, health + updateAmount);
		}
		if(ammo  <= maxAmmo) {
			updateAmount = (int)Math.ceil(maxAmmo * (percentage/100f));
			ammo = Math.min(maxAmmo, ammo + updateAmount);
		}
		if(fuel <= maxFuel) {
			updateAmount = (int)Math.ceil(maxHealth * (percentage/100f));
			fuel =  Math.min(maxFuel, fuel + updateAmount);
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

	public abstract UnitView registerListener();

	public abstract PerceivedUnit getView();
	
	public String toString(boolean toMonitor) {
		if(toMonitor) {
			return "ID: " + ID + "\n"
			+ "Pos: " + pos.toString() + "\n"
			+ "Health: " + health  + "/" + maxHealth + "\n"
			+ "Ammo: " + ammo  + "/" + maxAmmo + "\n"
			+ "Fuel: " + fuel  + "/" + maxFuel + "\n";
		}
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