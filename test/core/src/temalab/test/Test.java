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
	
	//Vehicle v;
	Map m;
	
	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		m = new Map(1000, 64);
		m.makeRandomMap();
		m.addVehicle(new Vehicle("green"), 0, 0);
		Vehicle v = new Vehicle("red");
		m.addVehicle(v, 1, 1);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		 /*
		if(Gdx.input.justTouched()) {
			v.setTarget(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()));
		}

		v.render(shapeRenderer, batch, font);
		*/
		m.render(shapeRenderer, batch, font);
		
		
		
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		
	}
}
