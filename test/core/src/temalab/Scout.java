package temalab;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import temalab.Field.Type;

public class Scout extends Unit {
    public Scout(Position pos, Team t) {
        super(pos, t);
        steppableTypes = new ArrayList<Field.Type>();
        steppableTypes.add(Type.GRASS);
        steppableTypes.add(Type.MARSH);
        maxHealth = 70;
        health = maxHealth;
        viewRange = 20;
        shootRange = 20;
        damage = 0;
        maxAmmo = 0;
        ammo = maxAmmo;
        maxFuel = 100;
        fuel = maxFuel;
        consumption = 6;
        actionPoints = 5;
        price = 250;

    }

    @Override
    public Texture getTexture() {
        return new Texture(Gdx.files.internal("binoculars.png"));
    }
}