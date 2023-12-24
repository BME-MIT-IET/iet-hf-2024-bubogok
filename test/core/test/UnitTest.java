import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;

import temalab.Field;
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
        m = null;
        m = Map.init(1000, 16, 1.5f);
        t1 = new Team("red", 5000);
        p = new Position(0, 0);
        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 16; j++) {
                m.addField(new Field(new Position(i, j), Field.Type.GRASS));
            }
        }
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

    @Test
    public void testMove() {
        var dest = Map.instance().getField(new Position(u.pos().x() + 1, u.pos().y()));
        assertEquals(p, u.pos());
        assertEquals(Field.Type.GRASS, dest.getType());
        u.move(dest);
        assertEquals(new Position(1, 0), u.pos());
    }

    @Test
    public void asdff() {
        assertEquals(p.toString(), u.pos().toString());
        for(int i = 1; i < 10; i++) {
            System.out.println(i);
            System.out.flush();
            var curr = Map.instance().getField(u.pos());
            var dest = Map.instance().getField(new Position(i, 1));
            assertTrue(curr.isNeighbouring(dest));
            assertEquals(true, u.steppables().contains(dest.getType()));

            u.move(Map.instance().getField(new Position(i, 1)));
            assertEquals(new Position(i, 1), u.pos());
        }
        assertEquals(new Position(3, 1), u.pos());
    }


    @AfterClass
    public static void dispose() {
        m = null;
    }
}
