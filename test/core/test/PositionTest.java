import org.junit.jupiter.api.Test;
import temalab.model.Position;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PositionTest {
	private final Position p1 = new Position(-1, 2);
	private final Position p2 = new Position(3, 5);
	private final Position p3 = new Position(0, 2);

	@Test
	void test_inDistance_true() {
		assertTrue(p1.inDistance(p3, 2f));
	}

	@Test
	void test_inDistance_false() {
		assertFalse(p1.inDistance(p2, 2f));
	}

	@Test
	void test_isNeighbouring_true() {
		assertTrue(p1.isNeighbouring(p3));
	}

	@Test
	void test_isNeighbouring_false() {
		assertFalse(p1.isNeighbouring(p2));
	}
}