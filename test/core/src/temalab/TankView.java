package temalab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class TankView extends UnitView {
    private static Texture texture = null;
    public TankView(Unit u, int sr, int vr, Color c) {
        super(u, sr, vr, c);
        if(texture == null) {
            texture = new Texture(Gdx.files.internal("tank.png"));
        }
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
    
}
