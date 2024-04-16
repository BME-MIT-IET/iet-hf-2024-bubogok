package temalab;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import temalab.Field.Type;

public class FieldView {
    private Color color;
    private Vector2 center;

    public FieldView(Field f) {
        center = f.pos().screenCoords();
        var t = f.getType();
        if(t == Type.GRASS) {
			this.color = new Color(0, 1, 0, 1);
		} else if(t == Type.WATER) {
			this.color = new Color(0, 0, 1, 1);
		} else if(t == Type.FOREST) {
			this.color = new Color(0.11f, 0.5f, 0.11f, 1);
		} else if(t == Type.BUILDING) {
			this.color = new Color(0.5f, 0.5f, 0.5f, 1);
		} else if(t == Type.MARSH) {
			this.color = new Color(0.47f, 0.35f, 0.23f, 1);
		}
    }
    public void render(ShapeRenderer sr, SpriteBatch sb) {
        float size = Map.instance().squareSize();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(color);
		sr.rect(center.x - size/2, center.y - size/2,	size, size);
		sr.end();
    }
}
