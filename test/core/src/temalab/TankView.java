package temalab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TankView extends UnitView {
    private static Texture texture = null;

    public TankView(Unit u) {
        super(u);
        if(texture == null) {
            texture = new Texture(Gdx.files.internal("tank.png"));
        }
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
    
}
