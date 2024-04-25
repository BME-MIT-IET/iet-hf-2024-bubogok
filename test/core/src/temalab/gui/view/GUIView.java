package temalab.gui.view;

import java.util.HashMap;
import java.util.Map;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import temalab.common.MainModel;
import temalab.common.MainModelListener;
import temalab.communicator.MainCommunicator;
import temalab.model.ControlPoint;
import temalab.model.Field;
import temalab.model.Team;
import temalab.model.Unit;


public class GUIView extends ApplicationAdapter implements MainModelListener{
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	BitmapFont font;
	private OrthographicCamera camera;
	int width;
	int height;
	int mapSize;


	private float squareSize;
	private float universalDistanceConstant;
	private Map<Unit, UnitView> unitViews;
	private Map<Field, FieldView> fieldViews;
    private Map<ControlPoint, ControlPointView> controlPointViews;
	private Map<Team, TeamView> teamViews;
	private MainModel mm;
	private MainCommunicator mc;

	public void init(MainModel mm, MainCommunicator mc, float sizingFactor) {
		universalDistanceConstant = sizingFactor;
		this.mm = mm;
		this.mc = mc;
		mc.setSteppability();
	}

	@Override
	public void create() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		Gdx.graphics.setContinuousRendering(true);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		int size = Math.min(width, height);
		squareSize = (size / universalDistanceConstant) / mm.width();

		
		fieldViews = new HashMap<>();
		unitViews = new HashMap<>();
		controlPointViews = new HashMap<>();
		teamViews = new HashMap<>();
		mm.addListener(this);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		Gdx.gl.glLineWidth(2);
		Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
		Gdx.gl.glScissor(0, 0, height, height);

		fieldViews.forEach((f, fv) -> {
			fv.render(shapeRenderer, batch);
		});
		unitViews.forEach((u, uv) -> {
			uv.render(shapeRenderer, batch);
		});
		controlPointViews.forEach((cp, cpv) -> {
			cpv.render(shapeRenderer, batch);
		});

		shapeRenderer.flush();
		Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);

		int i = 1;
		for(var tv : teamViews.values()) {
			int x = (width - height) *  i / (teamViews.size() + 1);
			tv.render(batch, font, height + x,  990);
			i++;
		}

		// if (t1.units().isEmpty()) {
		// 	try {
		// 		Thread.sleep(10000);
		// 	} catch (InterruptedException e) {}
		// 	TL1.endSimu(false);
		// 	TL2.endSimu(true);
		// 	dispose();
		// } else if (t2.units().isEmpty()) {
		// 	try {
		// 		Thread.sleep(10000);
		// 	} catch (InterruptedException e) {}
		// 	TL1.endSimu(true);
		// 	TL2.endSimu(false);
		// 	dispose();
		// }

		if (Gdx.input.isKeyPressed(Input.Keys.Q)) dispose();
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
			mc.change();
		}
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
		System.exit(0);
	}
	
	public float squareSize() {
		return squareSize;
	}
	
	public float universalDistanceConstant() {
		return universalDistanceConstant;
	}

	@Override
	public void unitCreated(Unit u) {
		unitViews.put(u, new UnitView(u, this));
	}

	@Override
	public void unitDestoryed(Unit u) {
		unitViews.remove(u);
	}
	
	@Override
	public void controlPointCreated(ControlPoint cp) {
		controlPointViews.put(cp, new ControlPointView(cp, this));
	}

	@Override
	public void controlPointDestoryed(ControlPoint cp) {
		controlPointViews.remove(cp);
	}

	@Override
	public void fieldCreated(temalab.model.Field f) {
		fieldViews.put(f, new FieldView(f, this));
	}

	@Override
	public void fieldDestroyed(temalab.model.Field f) {}

	@Override
	public void teamCreated(Team t) {
		teamViews.put(t, new TeamView(t));
	}

	@Override
	public void teamDestroyed(Team t) {
		teamViews.remove(t);
	}
}