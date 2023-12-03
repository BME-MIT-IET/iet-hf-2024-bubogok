import static org.junit.Assert.*;
import org.junit.*;

import temalab.Map;
import temalab.Position;
import temalab.Team;
import temalab.Unit;
import temalab.Unit.Type;

public class UnitTest {
    static Map m;
    static Unit u;
    static Position p;
    static Team t1;

    @BeforeClass
    public static void init() {
        m = Map.init(100, 4, 2);
        t1 = new Team("red");
        p = new Position(1, 1);
        u = new Unit(p, t1, Type.TANK);
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
