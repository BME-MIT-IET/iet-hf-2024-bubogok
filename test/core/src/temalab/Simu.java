package temalab;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Simu extends ApplicationAdapter {
	
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	BitmapFont font;
	private OrthographicCamera camera;

	static Map m;
	TeamLeader TL1;
	TeamLeader TL2;
	Team t1 = new Team("white");
	Team t2 = new Team("red");

	public void init() {
		
		m = Map.init(Gdx.graphics.getHeight(), 16, 1.5f);
		m.addControlPoint(new ControlPoint(new Position(10, 10), 10));
		m.addTeam(t1);
		m.addTeam(t2);
		demoUnits();
	}

	public void demoUnits() {
		t1.addUnit(new Tank(new Position(4, 4), t1));
		t1.addUnit(new Scout(new Position(10, 4), t1));
		t2.addUnit(new Scout(new Position(6, 6), t2));
		t2.addUnit(new Tank(new Position(7, 8), t2));
		t2.addUnit(new Infantry(new Position(10, 8), t2));
	}

	
	@Override
	public void create() {
		Gdx.graphics.setContinuousRendering(false);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		init();

		new Thread() {
			public void run() {
				TL1 = new TeamLeader(t1, "test1.py");
				TL2 = new TeamLeader(t2, "test2.py");
				while(true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					TL1.communicate();
					Map.instance().ControlPointsUpdate();
					Gdx.graphics.requestRendering();
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					TL2.communicate();
					Map.instance().ControlPointsUpdate();
					Gdx.graphics.requestRendering();
					
				}
			}
		}.start();
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		Gdx.gl.glLineWidth(2);
		Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
		Gdx.gl.glScissor(0, 0, Gdx.graphics.getHeight(), Gdx.graphics.getHeight());
		m.render(shapeRenderer, batch);
		shapeRenderer.flush();
		Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
		
		batch.begin();
		ArrayList<String> t1Monitor = t1.toMonitor();
		ArrayList<String> t2Monitor = t2.toMonitor();
		int offset = 990;
		for(var s : t1Monitor) {
			font.draw(batch, s, 1200, offset);
			offset -= 100;
		}
		offset = 990;
		for(var s : t2Monitor) {
			font.draw(batch, s, 1400, offset);
			offset -= 100; 
		}
		batch.end();
		
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
		System.exit(0);
	}
}