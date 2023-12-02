import static org.junit.Assert.*;

import org.junit.*;

import temalab.Field;
import temalab.Position;
import temalab.Field.Type;
import temalab.Map;


public class FieldTest {
    static Position p;
    Field f;
    static Map m;
    @BeforeClass
    public static void init() {
        p = new Position(1, 1);
        m = Map.init(100, 4, 2);
    }
    /*
     * Testing all getters
     */
    @Test
    public void ctorTest() {
        var f = new Field(p, Type.BUILDING);
        assertEquals(p.screenCoords(), f.getCenter());
        assertEquals(p, f.pos());
        assertEquals(p.toString(), f.pos().toString());
        assertEquals(Type.BUILDING, f.getType());
    }
}
