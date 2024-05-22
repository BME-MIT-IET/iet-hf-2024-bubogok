import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import temalab.model.ControlPoint;
import temalab.model.Position;

public class ControlPointTest {
	@Test
	public void controlPointIdIsIncremental() {
		ControlPoint controlPoint1 = new ControlPoint(new Position(0, 0), 0, 0, null);
		ControlPoint controlPoint2 = new ControlPoint(new Position(0, 1), 0, 0, null);
		ControlPoint controlPoint3 = new ControlPoint(new Position(0, 2), 0, 0, null);
		Assertions.assertNotEquals(controlPoint1.getId(), controlPoint3.getId());
		Assertions.assertNotEquals(controlPoint1.getId(), controlPoint2.getId());
		Assertions.assertNotEquals(controlPoint2.getId(), controlPoint3.getId());
	}
}
