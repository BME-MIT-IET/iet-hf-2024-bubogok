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
	TeamLeader tl;
	@Override
	public void create() {
		r = new Random();
		shapeRenderer = new ShapeRenderer();
		m = Map.init(1000, 64);
		m.makeRandomMap();
		Team t1 = new Team("green");
		Team t2 = new Team("red");
		m.addTeam(t1);
		m.addTeam(t2);
		t1.addUnit(new Vehicle(new Vector(0, 0)));
		t2.addUnit(new Vehicle(new Vector(1, 1)));
		new Thread() {
			public void run() {
				tl = new TeamLeader(t1);
				while(true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tl.doMagic();		
				}
			}
		}.start();
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