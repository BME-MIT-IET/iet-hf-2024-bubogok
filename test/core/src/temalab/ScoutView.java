package temalab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ScoutView extends UnitView {
    private static Texture texture = null;
    public ScoutView(Unit u) {
		super(u);
        if(texture == null) {
            texture = new Texture(Gdx.files.internal("scout.png"));
        }
	}

	@Override
    public Texture getTexture() {
        return texture;
    }
    
}
