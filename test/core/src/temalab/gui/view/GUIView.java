package temalab.gui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.checkerframework.checker.units.qual.s;

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
import temalab.model.ControlPoint;
import temalab.model.Field;
import temalab.model.Unit;


public class GUIView extends ApplicationAdapter implements MainModelListener{
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	BitmapFont font;
	private OrthographicCamera camera;


	private float squareSize;
	private float universalDistanceConstant;
	private Map<Unit, UnitView> unitViews;
	private Map<Field, FieldView> fieldViews;
    private Map<ControlPoint, ControlPointView> controlPointViews;
	private MainModel mm;

	public void addMM(MainModel mm, int size, float sizingFactor) {
		this.mm = mm;
		universalDistanceConstant = sizingFactor;
		squareSize = (size / universalDistanceConstant) / mm.width();
		System.err.println(squareSize);
	}

	@Override
	public void create() {
		Gdx.graphics.setContinuousRendering(true);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		fieldViews = new HashMap<>();
		unitViews = new HashMap<>();
		controlPointViews = new HashMap<>();

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
		Gdx.gl.glScissor(0, 0, Gdx.graphics.getHeight(), Gdx.graphics.getHeight());

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

		// batch.begin();
		// ArrayList<String> t1Monitor = t1.teamMembersToString(true);
		// ArrayList<String> t2Monitor = t2.teamMembersToString(true);
		// int offset = 990;
		// for (var s : t1Monitor) {
		// 	font.draw(batch, s, 1200, offset);
		// 	offset -= 120;
		// }
		// offset = 990;
		// for (var s : t2Monitor) {
		// 	font.draw(batch, s, 1400, offset);
		// 	offset -= 120;
		// }
		// batch.end();

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
		// if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
		// 	synchronized (waiter) {
		// 		if (pause) {
		// 			pause = false;
		// 			waiter.notifyAll();
		// 			System.out.println("RESUMED");
		// 		} else {
		// 			pause = true;
		// 			System.out.println("PAUSED");
		// 		}
		// 	}
		// 	System.out.println("paused = " + pause);
		// }
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
		System.exit(0);
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

	public float squareSize() {
		return squareSize;
	}

	public float universalDistanceConstant() {
		return universalDistanceConstant;
	}

	@Override
	public void fieldCreated(temalab.model.Field f) {
		fieldViews.put(f, new FieldView(f, this));
	}

	@Override
	public void fieldDestroyed(temalab.model.Field f) {}
}