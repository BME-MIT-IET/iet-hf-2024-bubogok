package temalab;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.graphics.Color;

public class Unit {
	private final int ID;
	private Field field;
	private ArrayList<Field> seenFields;
	private ArrayList<PerceivedUnit> seenUnits;
	private ArrayList<ControlPoint> seenControlPoints;
	private ArrayList<Field.Type> steppableTypes;
	private Team team;
	private Type type;
	private UnitListener listener;

	private int health;
	private int maxHealth;
	private int viewRange;
	private int shootRange;
	private int damage;
	private int ammo;
	private int maxAmmo;
	private int fuel;
	private int maxFuel;
	private int consumption;
	private int price;
	private int maxActionPoints;
	private int actionPoints;
	private static Scanner sc;
	
	public enum Type {
		SCOUT,
		TANK,
		INFANTRY
	}

	public Unit(Position pos, Team team, Type type) {
		seenFields = new ArrayList<Field>();
		seenUnits = new ArrayList<PerceivedUnit>();
		steppableTypes = new ArrayList<Field.Type>();
		ID = Map.instance().r.nextInt(1000000);
		this.field = Map.instance().getField(pos);
		field.arrive(this);
		this.team = team;
		team.addUnit(this);
		this.type = type;
        try {

			//TODO: a steppabletyes beolvasása is lehetne a txtből
			if(type == Unit.Type.TANK) {
				sc = new Scanner(new File("desciptors/tankStats.txt"));
				steppableTypes.add(Field.Type.GRASS);
        		steppableTypes.add(Field.Type.MARSH);
			} else if(type == Unit.Type.INFANTRY) {
				sc = new Scanner(new File("desciptors/infantryStats.txt"));
				steppableTypes.add(Field.Type.GRASS);
				steppableTypes.add(Field.Type.MARSH);
				steppableTypes.add(Field.Type.FOREST);
				steppableTypes.add(Field.Type.WATER);
				steppableTypes.add(Field.Type.BUILDING);
			} else if(type == Unit.Type.SCOUT) {
				sc = new Scanner(new File("desciptors/scoutStats.txt"));
				steppableTypes.add(Field.Type.GRASS);
        		steppableTypes.add(Field.Type.MARSH);
			}
            while(sc.hasNextLine()) {
                maxHealth = Integer.parseInt(sc.nextLine());
                viewRange = Integer.parseInt(sc.nextLine());
                shootRange = Integer.parseInt(sc.nextLine());
                damage = Integer.parseInt(sc.nextLine());
                maxAmmo = Integer.parseInt(sc.nextLine());
                maxFuel = Integer.parseInt(sc.nextLine());
                consumption = Integer.parseInt(sc.nextLine());
                maxActionPoints = Integer.parseInt(sc.nextLine());
                price = Integer.parseInt(sc.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        health = maxHealth;
        ammo = maxAmmo;
        fuel = maxFuel;
        actionPoints = maxActionPoints;
	}

	public void move(Field dest) {
		System.err.println("moveba azért belépünk: curr: " +  field.pos().toString() + " dest: " + dest.pos().toString());
		if(fuel - consumption >= 0 && actionPoints > 0 && field.isNeighbouring(dest)) {
			System.out.println("elso");
			if(steppableTypes.contains(dest.getType())) {
				System.out.println("2");
				if(dest.arrive(this)) {
					System.out.println("3");
					field.leave();
					field = dest;
					fuel -= consumption;
					actionPoints--;
				}
			}
		}
	}

	public void shoot(Field target) {
		//TODO: 0.5 offset a posban kellene
		if (ammo > 0 && actionPoints > 0) {
			if(field.inDistance(target, shootRange + 0.5f)) {
				target.takeShot(damage);
				if(listener != null) {
					listener.onShoot(target.pos());
				}
				ammo--;
				actionPoints--;
			}			
		}
	}

	public void takeShot(int recievedDamage) {	
		health -= recievedDamage;
		if(health <= 0) {
			team.unitDied(ID);
			if(listener != null) {
				listener.unitDied();
			}
		}
	}
	
	public void updateWorld() {
		seenFields = Map.instance().requestFileds(field.pos(), viewRange + 0.5f);
		seenUnits = Map.instance().requestPerceivedUnits(field.pos(), viewRange + 0.5f);
		seenControlPoints = Map.instance().requestControlPoints(field.pos(), viewRange + 0.5f);
	}

	public void refillActionPoints() {
		actionPoints = maxActionPoints;
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
		return field.pos();
	}

	public Team team() {
		return team;
	}
	public int shootRange() {
		return shootRange;
	}
	public Color color() {
		return team.getColor();
	}
	public int viewRange() {
		return viewRange;
	}

	public void registerListener(UnitListener ul) {
		listener = ul;
	}

	public Type type() {
		return type;
	}

	public int price() {
		return price;
	}

	public ArrayList<Field.Type> steppables() {
		return steppableTypes;
	}

	public PerceivedUnit getPerception() {
		return new PerceivedUnit(field.pos().toString(), team.getName(), type.toString());
	}
	
	public int actionPoints() {
		return actionPoints;
	}
	public String toString(boolean toMonitor) {
		if(toMonitor) {
			return "ID: " + ID + "\n"
			+ "Pos: " + field.pos().toString() + "\n"
			+ "Health: " + health  + "/" + maxHealth + "\n"
			+ "Ammo: " + ammo  + "/" + maxAmmo + "\n"
			+ "Fuel: " + fuel  + "/" + maxFuel + "\n";
		}
		return "\n" + ID + "\n"
		+ type.toString() + "\n"
		+ field.pos().toString() + " " + field.getType().toString() + "\n"
	 	+ seenFields.toString() + "\n"
		+ seenUnits.toString() + "\n"
		+ seenControlPoints.toString() + "\n"
		+ health + "\n"
		+ ammo + "\n"
		+ fuel + "\n"
		+ actionPoints + "\n"
		+ team.getName() + "\n";
	}
}