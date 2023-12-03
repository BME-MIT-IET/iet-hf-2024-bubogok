import static org.junit.Assert.*;
import org.junit.*;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

import temalab.Map;
import temalab.Position;
import temalab.Scout;
import temalab.Simu;
import temalab.Team;
import temalab.Unit;

public class UnitTest {
    static Map m;
    static Unit u;
    static Position p;
    static Team t1;
    static SimuTest s;

    @BeforeClass
    public static void init() {
        s = new SimuTest(new Simu());
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new Simu(), config);
        m = Map.init(100, 4, 2);
        s.addMap(m);
        t1 = new Team("red");
        p = new Position(1, 1);
        u = new Scout(p, t1);
    }
    /*
     * Testint getters
     */
    @Test
    public void ctorTest() {
        assertEquals(u.pos(), p);
        assertEquals(u.team(), t1);
    }

    
}
