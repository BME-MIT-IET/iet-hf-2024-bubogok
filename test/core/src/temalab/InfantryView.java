package temalab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class InfantryView extends UnitView {
    private static Texture texture = null;
    public InfantryView(Unit u) {
        super(u);
        if(texture == null) {
            texture = new Texture(Gdx.files.internal("infantry.png"));
        }
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
    
}
