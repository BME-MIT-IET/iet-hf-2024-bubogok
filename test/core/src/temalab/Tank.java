package temalab;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import temalab.Field.Type;

public class Tank extends Unit {
    public Tank(Position pos, Team t) {
        super(pos, t);
        steppableTypes = new ArrayList<Field.Type>();
        steppableTypes.add(Type.GRASS);
        steppableTypes.add(Type.MARSH);
        maxHealth = 80;
        health = maxHealth;
        viewRange = 8;
        shootRange = 7;
        damage = 30;
        maxAmmo = 6;
        ammo = maxAmmo;
        maxFuel = 100;
        fuel = maxFuel;
        consumption = 10;
        actionPoints = 2;
        price = 400;
    }

    @Override
    public Texture getTexture() {
        return new Texture(Gdx.files.internal("tank.png"));
    }

    @Override
    public Unit shallowCopy() {
        Tank s = new Tank(pos, team);
        s.seenUnits = new ArrayList<Unit>();
        s.seenFields = new ArrayList<Field>();
        s.seenControlPoints = new ArrayList<ControlPoint>();
        return s;
    }
    
}
