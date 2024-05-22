package temalab.model;

import com.badlogic.gdx.graphics.Color;
import temalab.common.MainModel;
import temalab.common.UnitListener;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Unit {
	private static final Path descriptorDir = Path.of("assets", "descriptors");

	private final int id;
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
	private Position statingPos;

	private static int idCounter = 0;

	public enum Type {
		SCOUT("SCOUT.txt"),
		TANK("TANK.txt"),
		INFANTRY("INFANTRY.txt");

		public final Path path;

		Type(String sourceFileName) {
			path = Path.of(sourceFileName);
		}
	}

	public Unit(Field f, Team team, Type type) {
		seenFields = new ArrayList<>();
		seenUnits = new ArrayList<>();
		steppableTypes = new ArrayList<>();
		id = idCounter++;
		this.field = f;
		field.arrive(this);
		this.team = team;
		team.addUnit(this);
		this.type = type;
		this.statingPos = f.pos();
		try (Scanner sc = new Scanner(descriptorDir.resolve(type.path))) {
			maxHealth = Integer.parseInt(sc.nextLine());
			viewRange = Integer.parseInt(sc.nextLine());
			shootRange = Integer.parseInt(sc.nextLine());
			damage = Integer.parseInt(sc.nextLine());
			maxAmmo = Integer.parseInt(sc.nextLine());
			maxFuel = Integer.parseInt(sc.nextLine());
			consumption = Integer.parseInt(sc.nextLine());
			maxActionPoints = Integer.parseInt(sc.nextLine());
			price = Integer.parseInt(sc.nextLine());
			int dummy = Integer.parseInt(sc.nextLine());
			while (sc.hasNextLine()) {
				steppableTypes.add(Field.Type.valueOf(sc.nextLine()));
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
		if (actionPoints <= 0) {
			throw new RuntimeException("move out of actionPoints: " + this.actionPoints + " id: " + this.id);
		}
		if (fuel < consumption) {
			throw new RuntimeException("move out of fuel: " + this.fuel + " id: " + this.id);
		}
		if (!field.isNeighbouring(dest)) {
			throw new RuntimeException("move is not neightbouring id: " + this.id + " dest: " + dest.toString() + " curr: " + this.field.pos().toString());
		}
		if (!steppableTypes.contains(dest.getType())) {
			throw new RuntimeException("move is not steppable id: " + this.id);
		}
		if (!dest.arrive(this)) {
			// throw new RuntimeException("move cannot arrive id: " + this.ID);
			return;
		}
		if (dest == field) {
			return;
		}
		field.leave();
		field = dest;
		// fuel -= consumption;
		actionPoints--;

	}

	public void shoot(Field target) {
		if (actionPoints <= 0) {
			throw new RuntimeException("shoot out of actionPoints: " + this.actionPoints + " id: " + this.id);
		}
		if (ammo <= 0) {
			throw new RuntimeException("shoot out of ammo: " + this.ammo + " id: " + this.id);
		}
		if (!field.inDistance(target, shootRange + 0.5f)) {
			throw new RuntimeException("shoot is not in dist: " + this.id);
		}

		target.takeShot(damage);
		if (listener != null) {
			listener.onShoot(target.pos());
		}
		ammo--;
		actionPoints--;
	}

	public void takeShot(int recievedDamage) {
		health -= recievedDamage;
		if (health <= 0) {
			field.leave();
			team.unitDied(id);
			if (listener != null) {
				listener.unitDied();
			}
		}
	}

	public void updateWorld(MainModel mm) {
		seenFields = mm.requestFileds(field.pos(), viewRange + 0.5f);
		seenUnits = mm.requestPerceivedUnits(field.pos(), viewRange + 0.5f);
		seenControlPoints = mm.requestControlPoints(field.pos(), viewRange + 0.5f);
	}

	public void refillActionPoints() {
		actionPoints = maxActionPoints;
	}

	public void updateSelf(int percentage) {
		int updateAmount;
		if (health <= maxHealth) {
			updateAmount = (int) Math.ceil(maxHealth * (percentage / 100f));

			health = Math.min(maxHealth, health + updateAmount);
		}
		if (ammo <= maxAmmo) {
			updateAmount = (int) Math.ceil(maxAmmo * (percentage / 100f));
			ammo = Math.min(maxAmmo, ammo + updateAmount);
		}
		if (fuel <= maxFuel) {
			updateAmount = (int) Math.ceil(maxHealth * (percentage / 100f));
			fuel = Math.min(maxFuel, fuel + updateAmount);
		}
	}

	public int getUUID() {
		return this.id;
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
		return new PerceivedUnit(field.pos().toString(), team.getName(), type.toString(), id, health);
	}

	public int actionPoints() {
		return actionPoints;
	}

	public Position getStartingPos() {
		return statingPos;
	}

	public String toString(boolean toMonitor) {
		if (toMonitor) {
			return "ID: " + id + "\n"
					+ "Type: " + type.toString() + "\n"
					+ "Pos: " + field.pos().toString() + "\n"
					+ "Health: " + health + "/" + maxHealth + "\n"
					+ "Ammo: " + ammo + "/" + maxAmmo + "\n"
					+ "Fuel: " + fuel + "/" + maxFuel + "\n";
		}
		return "\n" + id + "\n"
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

	public void reset(Field f) {
		health = maxHealth;
		ammo = maxAmmo;
		fuel = maxFuel;
		actionPoints = maxActionPoints;
		this.field.leave();
		f.arrive(this);
		this.field = f;
		if (listener != null) {
			listener.unitReseted();
		}
	}

	public void setField(Field f) {
		this.field.leave();
		f.arrive(this);
		this.field = f;
	}

	public void setActionPoints(int actionPoints) {
		this.actionPoints = actionPoints;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getAmmo() {
		return ammo;
	}

	public int getActionPoints() {
		return actionPoints;
	}

	public Field getField() {
		return field;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getHealth() {
		return health;
	}

	public Team getTeam() {
		return team;
	}
}
