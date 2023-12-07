package temalab;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MapView {
    private ArrayList<FieldView> fieldViews;

    public MapView(Map m) {
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
            fieldViews.add(new FieldView(f));
        }
    }
}
