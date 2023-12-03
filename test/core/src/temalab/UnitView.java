package temalab;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class UnitView implements UnitListener{
    protected Unit u;
    private boolean currentlyShooting;
    protected Position shootingPos;
    protected int shootRange;
    protected int viewRange;
	protected Color c;

    public UnitView(Unit u, int sr, int vr, Color c) {
        this.u = u;
        currentlyShooting = false;
        shootingPos = null;
        shootRange = sr;
        viewRange = vr;
		this.c = c;
    }

    public void render(ShapeRenderer sr, SpriteBatch sb) {
		float size = Map.instance().squareSize();
		Vector2 center = u.pos().screenCoords();
		
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(c);
		sr.circle(center.x, center.y, size / 2);
		sr.end();
		
		sb.begin();
		sb.draw(getTexture(), center.x - (size / 2), center.y - (size / 2), size, size);
		sb.end();
		
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setColor(c);
		sr.circle(center.x, center.y, Map.instance().universalDistanceConstant() * size * shootRange);
		sr.circle(center.x, center.y, Map.instance().universalDistanceConstant() * size * viewRange);
		sr.end();
		if(currentlyShooting) {
			sr.begin(ShapeRenderer.ShapeType.Line);
			sr.setColor(c);
			sr.line(center, shootingPos.screenCoords());
			sr.end();
			currentlyShooting = false;
		}
	}

    public abstract Texture getTexture();

    @Override
    public void onShootStart(Position p) {
        currentlyShooting = true;
        shootingPos = p;
    }
}
