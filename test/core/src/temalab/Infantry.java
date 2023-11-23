package temalab;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import temalab.Field.Type;

public class Infantry extends Unit {
    private static Texture texture = null;
    public Infantry(Position pos, Team t) {
        super(pos, t);
        steppableTypes = new ArrayList<Field.Type>();
        steppableTypes.add(Type.GRASS);
        steppableTypes.add(Type.MARSH);
        steppableTypes.add(Type.FOREST);
        steppableTypes.add(Type.WATER);
        steppableTypes.add(Type.BUILDING);
        if(texture == null) {
            texture = new Texture(Gdx.files.internal("infantry.png"));
        }
        try {
            Scanner sc = new Scanner(new File("infantryStats.txt"));
            while(sc.hasNextLine()) {
                maxHealth = Integer.parseInt(sc.nextLine());
                viewRange = Integer.parseInt(sc.nextLine());
                shootRange = Integer.parseInt(sc.nextLine());
                damage = Integer.parseInt(sc.nextLine());
                maxAmmo = Integer.parseInt(sc.nextLine());
                maxFuel = Integer.parseInt(sc.nextLine());
                consumption = Integer.parseInt(sc.nextLine());
                actionPoints = Integer.parseInt(sc.nextLine());
                price = Integer.parseInt(sc.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        health = maxHealth;
        ammo = maxAmmo;
        fuel = maxFuel;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public UnitView getView() {
        return new UnitView(pos, team);
    }
}
