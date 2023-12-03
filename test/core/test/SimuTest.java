import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.backends.headless.*;

import temalab.Map;

public class SimuTest extends HeadlessApplication {

    public SimuTest(ApplicationListener listener) {
        super(listener);
    }

    ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	BitmapFont font;
    Map m;


    public void addMap(Map m) {
       this.m = m; 
    }
}
