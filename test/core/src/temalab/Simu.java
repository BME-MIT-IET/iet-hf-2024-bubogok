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
	private ArrayList<UnitView> unitViews;
	private ArrayList<ControlPointView> controlPointViews;
	private MapView mapView;
	static Map m;
	TeamLeader TL1;
	TeamLeader TL2;
	Team t1 = new Team("white", 5000);
	Team t2 = new Team("red", 5000);

	public void init() {
		m = Map.init(Gdx.graphics.getHeight(), 16, 1.5f);
		m.makeSimplexNoiseMap();
		unitViews = new ArrayList<UnitView>();
		controlPointViews = new ArrayList<ControlPointView>();
		mapView = new MapView(m);
		m.addTeam(t1);
		m.addTeam(t2);
		demoUnits();
		demoCPs();
		TL1 = new TeamLeader(t1, "test1.py");
		TL2 = new TeamLeader(t2, "test2.py");
		TL1.registerUnit();
	}

	public void demoUnits() {
		var u1 = new Unit(new Position(4, 4), t1, Unit.Type.TANK);
		var u2 = new Unit(new Position(10, 4), t1, Unit.Type.INFANTRY);
		var u3 = new Unit(new Position(6, 6), t2, Unit.Type.SCOUT);
		var u4 = new Unit(new Position(7, 8), t2, Unit.Type.INFANTRY);
		var u5 = new Unit(new Position(10, 8), t2, Unit.Type.TANK);

		unitViews.add(new UnitView(u1));
		unitViews.add(new UnitView(u2));
		unitViews.add(new UnitView(u3));
		unitViews.add(new UnitView(u4));
		unitViews.add(new UnitView(u5));
	}

	public void demoCPs() {
		var cp1 = new ControlPoint(new Position(10, 10), 10, 2);
		m.addControlPoint(cp1);
		controlPointViews.add(new ControlPointView(cp1));
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


		
		mapView.render(shapeRenderer, batch);
		for(var uv: unitViews) {
			uv.render(shapeRenderer, batch);
		}
		for(var cpv : controlPointViews) {
			cpv.render(shapeRenderer, batch);
		}

		shapeRenderer.flush();
		Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);


		
		
		batch.begin();
		ArrayList<String> t1Monitor = t1.teamMembersToString(true);
		ArrayList<String> t2Monitor = t2.teamMembersToString(true);
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
		if(t1.units().isEmpty()) {
			TL1.endSimu(false);
			TL2.endSimu(true);
			dispose();
		} else if(t2.units().isEmpty()) {
			TL1.endSimu(true);
			TL2.endSimu(false);
			dispose();
		}
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
		System.exit(0);
	}
}