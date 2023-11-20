package temalab;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import temalab.Field.Type;

public class Infantry extends Unit {
    public Infantry(Position pos, Team t) {
        super(pos, t);
        steppableTypes = new ArrayList<Field.Type>();
        steppableTypes.add(Type.GRASS);
        steppableTypes.add(Type.MARSH);
        steppableTypes.add(Type.FOREST);
        steppableTypes.add(Type.WATER);
        steppableTypes.add(Type.BUILDING);
        maxHealth = 20;
        health = maxHealth;
        viewRange = 11;
        shootRange = 9;
        damage = 10;
        maxAmmo = 10;
        ammo = maxAmmo;
        maxFuel = 100;
        fuel = maxFuel;
        consumption = 4;
        actionPoints = 2;
        price = 100;
    }

    @Override
    public Texture getTexture() {
        return new Texture(Gdx.files.internal("infantry.java"));
    }
    
}
