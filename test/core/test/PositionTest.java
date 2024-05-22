import org.junit.Test;
import temalab.model.Position;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PositionTest {
	private final Position p1 = new Position(-1, 2);
	private final Position p2 = new Position(3, 5);
	private final Position p3 = new Position(0, 2);

	@Test
	public void test_inDistance_true() {
		assertTrue(p1.inDistance(p3, 2f));
	}

	@Test
	public void test_inDistance_false() {
		assertFalse(p1.inDistance(p2, 2f));
	}

	@Test
	public void test_isNeighbouring_true() {
		assertTrue(p1.isNeighbouring(p3));
	}

	@Test
	public void test_isNeighbouring_false() {
		assertFalse(p1.isNeighbouring(p2));
	}
}