import static org.junit.Assert.*;

import org.junit.*;

import com.badlogic.gdx.math.Vector2;

import temalab.Position;
import temalab.Map;

public class PositionTest {
    /*
     * Making a new position at (0, 0), getters should give 0, 0
     */
    @Test
    public void ctorTest() {
        var p = new Position(0, 0);
        assertEquals(0, p.x());
        assertEquals(0, p.y());
    }

    /*
     * Testing copy constructor
     */
    @Test
    public void copyctorTest() {
        var p2 = new Position(0, 0);
        var p = new Position(p2);
        assertEquals(0, p.x());
        assertEquals(0, p.y());
    }

    /*
     * Testing overwritten toString() method
     */
    @Test
    public void toStringTest() {
        var p = new Position(0, 0);
        assertEquals("0 0", p.toString());
    }

    /*
     * Testing converting to screen coordinates
     */
    //TODO:will fail, if screencords calculation method changes
    @Test
    public void testScreenCords() {
        final  Map m = Map.init(100, 4, 2);
        var p = new Position(3, 3);
        var screenCoords = p.screenCoords();
        assertEquals(new Vector2(100,100), screenCoords);
    }
    /*
     * Testing inDistance function
     * Make one Position at (2, 2), another at (5, 6)
     * With the range of vision at 5, one sees the other,
     * while with RoV as 4, it does not.
     */
    @Test
    public void testInDistance() {
        var p = new Position(2, 2);
        var p2 = new Position(5, 6);
        assertTrue(p.inDistance(p2, 5f));
        assertFalse(p.inDistance(p2, 4f));
    }
    /*
     * Testing inNeighbouring function
     * Make one Position at (0, 0), one at (1, 1), a third at (2, 2)
     * First should not be the neighbour of the third
     * Second shoudl be the neighbour of both of them
     */
    @Test
    public void testIsNeightbouring() {
        var p = new Position(0, 0);
        var p2 = new Position(1, 1);
        var p3 = new Position(2, 2);
        assertFalse(p.isNeighbouring(p3));
        assertTrue(p2.isNeighbouring(p));
        assertTrue(p2.isNeighbouring(p3));
    }

    /*
     * Tesing overwritten hashCode function
     * Two Positions should not have the same hashCode
     */
    @Test
    public void testHashCode() {
        var p = new Position(1, 1);
        var p2 = new Position(3, 3);
        assertNotEquals(p.hashCode(), p2.hashCode());
    }

    /*
     * Testing overwritten equals function
     * Two Positions should be equal, if their coordinates are the same
     */
    @Test
    public void testEquals() {
        var p = new Position(0, 0);
        var p2 = new Position(0, 0);
        var p3 = new Position(1, 1);
        assertTrue(p.equals(p2));
        assertFalse(p.equals(p3));
    }
}
