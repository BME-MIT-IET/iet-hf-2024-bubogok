package temalab;

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
	TeamLeader TL1;
	TeamLeader TL2;
	
	@Override
	public void create() {
		r = new Random();
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		m = Map.init(1000, 32);
		m.makeRandomMap();
		var cp = new ControlPoint(new Position(12, 12), 10);
		m.addControlPoint(cp);
		Team t1 = new Team("white");
		Team t2 = new Team("red");
		m.addTeam(t1);
		m.addTeam(t2);
		t1.addUnit(new Scout(new Position(6, 6), t1));
		t2.addUnit(new Scout(new Position(4, 4), t2));
		t1.addUnit(new Tank(new Position(7, 8), t2));
		new Thread() {
			public void run() {
				TL1 = new TeamLeader(t1);
				TL2 = new TeamLeader(t2);
				while(true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					TL1.communicate();
					Map.instance().ControlPointsUpdate();
					TL2.communicate();
					Map.instance().ControlPointsUpdate();
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
		System.exit(0);
	}
}