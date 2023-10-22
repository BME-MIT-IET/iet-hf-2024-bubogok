package temalab.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Test extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	BitmapFont font;
	
	static Map m;
	Team t1;
	Team t2;
	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		m = Map.init(1000, 64);
		m.makeRandomMap();
		t1 = new Team("green");
		t2 = new Team("red");
		m.addVehicle(new Vehicle(new Vector(0, 0), t1));
		m.addVehicle(new Vehicle(new Vector(1, 1), t2));
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