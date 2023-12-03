package temalab;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import temalab.Field.Type;

public class Scout extends Unit {
    public Scout(Position pos, Team t) {
        super(pos, t);
        steppableTypes = new ArrayList<Field.Type>();
        steppableTypes.add(Type.GRASS);
        steppableTypes.add(Type.MARSH);
        try {
            Scanner sc = new Scanner(new File("scoutStats.txt"));
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

    @Override
    public PerceivedUnit getView() {
        return new PerceivedUnit(pos, team);
    }
}