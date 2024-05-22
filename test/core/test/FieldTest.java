import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import temalab.model.Field;
import temalab.model.Field.Type;
import temalab.model.Position;
import temalab.model.Unit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FieldTest {

	private Field field;


	@Mock
	private Position mockPosition;
	@Mock
	private Unit mockUnit;
	@Mock
	private Unit mockOtherUnit;

	@BeforeEach
	public void init() {
		field = new Field(mockPosition, Type.GRASS);
	}

	@Test
	void test_arrive_OK() {
		assertTrue(field.arrive(mockUnit));
	}

	@Test
	void test_arrive_occupied() {
		field.arrive(mockOtherUnit);
		assertFalse(field.arrive(mockUnit));
	}

	@Test
	void test_takeShot() {
		field.arrive(mockUnit);
		field.takeShot(5);
		verify(mockUnit, times(1)).takeShot(5);
	}
}
