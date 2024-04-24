package temalab.gui.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import temalab.common.UnitListener;
import temalab.model.Position;
import temalab.model.Unit;
import temalab.model.Unit.Type;

public class UnitView implements UnitListener {
    private Unit u;
    private boolean currentlyShooting;
    private Position shootingPos;
    private int shootRange;
    private int viewRange;
	private Color c;
	private Texture texture;
	private boolean visibility;
	private GUIView gv;

    public UnitView(Unit u, GUIView guiv) {
        this.u = u;
		this.gv = guiv;
		u.registerListener(this);
        currentlyShooting = false;
		visibility = true;
        shootRange = u.shootRange();
        viewRange = u.viewRange();
		this.c = u.color();
		if(u.type() == Type.TANK && texture == null) {
			texture = new Texture(Gdx.files.internal("images/tank.png"));
		} else if(u.type() == Type.SCOUT && texture == null) {
			texture = new Texture(Gdx.files.internal("images/scout.png"));
		} else if(u.type() == Type.INFANTRY && texture == null) {
			texture = new Texture(Gdx.files.internal("images/infantry.png"));
		}
    }

    public void render(ShapeRenderer sr, SpriteBatch sb) {
		if(visibility) {
			float size = gv.squareSize();
			Vector2 center = u.pos().screenCoords(gv.squareSize(), gv.universalDistanceConstant());
			
			sr.begin(ShapeRenderer.ShapeType.Filled);
			sr.setColor(c);
			sr.circle(center.x, center.y, size / 2);
			sr.end();
			
			sb.begin();
			sb.draw(texture, center.x - (size / 2), center.y - (size / 2), size, size);
			sb.end();
			
			sr.begin(ShapeRenderer.ShapeType.Line);
			sr.setColor(c);
			sr.circle(center.x, center.y, gv.universalDistanceConstant() * size * shootRange);
			sr.circle(center.x, center.y, gv.universalDistanceConstant() * size * viewRange);
			sr.end();
			if(currentlyShooting) {
				sr.begin(ShapeRenderer.ShapeType.Line);
				sr.setColor(c);
				sr.line(center, shootingPos.screenCoords(gv.squareSize(), gv.universalDistanceConstant()));
				sr.end();
				currentlyShooting = false;
			}
		}
	}

    @Override
    public void onShoot(Position p) {
        currentlyShooting = true;
        shootingPos = p;
    }

	@Override
	public void unitDied() {
		gv.unitDestoryed(u);
	}
}