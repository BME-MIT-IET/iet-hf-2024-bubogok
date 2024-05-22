import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import temalab.model.Position;


public class PositionTest {
	private final Position p1 = new Position(-1, 2);
	private final Position p2 = new Position(3, 5);
	private final Position p3 = new Position(0, 2);

	@Test
	public void test_inDistance_true() {
		Assertions.assertTrue(p1.inDistance(p3, 2f));
	}

	@Test
	public void test_inDistance_false() {
		Assertions.assertFalse(p1.inDistance(p2, 2f));
	}

	@Test
	public void test_isNeighbouring_true() {
		Assertions.assertTrue(p1.isNeighbouring(p3));
	}

	@Test
	public void test_isNeighbouring_false() {
		Assertions.assertFalse(p1.isNeighbouring(p2));
	}
}