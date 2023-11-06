package temalab;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class ControlPoint {
    private Position pos;
    private float size;
    private Vector2 center;


    public ControlPoint(Position p) {
        pos = p;
        size = 4 * Map.instance().squareSize();
        center = pos.screenCoords();
    }

    public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
        sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setColor(Color.BLACK);
        sr.rect(center.x - size/2, center.y - size/2, size, size);
		sr.end();
    }  
}
