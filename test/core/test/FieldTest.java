import static org.junit.Assert.*;

import org.junit.*;

import temalab.model.Field;
import temalab.model.Map;
import temalab.model.Position;
import temalab.model.Team;
import temalab.model.Unit;
import temalab.model.Field.Type;


public class FieldTest {
    static Position p;
    Field f;
    static Map m;
    static Team t1;
    @BeforeClass
    public static void init() {
        m = null;
        p = new Position(1, 1);
        m = Map.init(1000, 16, 1.5f);
        t1 = new Team("red", 1000);
    }
    /*
     * Testing all getters
     */
    @Test
    public void ctorTest() {
        var f = new Field(p, Type.BUILDING);
        assertEquals(p, f.pos());
        assertEquals(p.toString(), f.pos().toString());
        assertEquals(Type.BUILDING, f.getType());
    }

    @Test
    public void testArrive() {
        var f = new Field(p, Type.GRASS);
        var u = new Unit(p, t1, Unit.Type.TANK);
        assertTrue(f.arrive(u));
        var u2 = new Unit(p, t1, Unit.Type.TANK);
        assertFalse(f.arrive(u2));
        f.leave();
        assertTrue(f.arrive(u2));
    }
    @AfterClass
    public static void dispose() {
        m = null;
    }
}
