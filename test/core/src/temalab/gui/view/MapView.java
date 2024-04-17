package temalab.gui.view;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import temalab.model.Field;
import temalab.model.Map;

public class MapView {

    private Map m;
    private ArrayList<FieldView> fieldViews;

    private float squareSize;
	private float universalDistanceConstant;

    public MapView(Map m, int size, float sizingFactor) {
        this.m = m;
        universalDistanceConstant = sizingFactor;
		squareSize = (size / universalDistanceConstant) / m.numberOfSquares();
        fieldViews = new ArrayList<FieldView>();
        m.registerListener(this);
    }

    public void render(ShapeRenderer sr, SpriteBatch sb) {
        for(var fv : fieldViews) {
			fv.render(sr, sb);
		}
	}
    public void fields(ArrayList<Field> fields) {
        for(var f : fields) {
            fieldViews.add(new FieldView(f, this));
        }
    }

    public float squareSize() {
		return squareSize;
	}

	public float universalDistanceConstant() {
		return universalDistanceConstant;
	}
}
