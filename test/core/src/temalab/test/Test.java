package temalab.test;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Test extends ApplicationAdapter {
	public static Random r;
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	BitmapFont font;
	
	static Map m;
	Team t1;
	Team t2;
	TeamLeader tl;
	@Override
	public void create() {
		r = new Random();
		shapeRenderer = new ShapeRenderer();
		m = Map.init(1000, 64);
		tl = new TeamLeader(t1);
		m.makeRandomMap();
		t1 = new Team("green");
		t2 = new Team("red");
		m.addVehicle(new Vehicle(new Vector(0, 0), t1));
		m.addVehicle(new Vehicle(new Vector(1, 1), t2));
		tl.sendMessage(m.getUUIDs());
		String[] commands = tl.getInput();
		int id = Integer.parseInt(commands[0]);
		Vector vec = new Vector( Integer.parseInt(commands[2]),  Integer.parseInt(commands[2]));
		Vehicle v = m.findByUUID(id);
		v.move(vec.x(), vec.y());
		
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		m.render(shapeRenderer, batch, font);
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}