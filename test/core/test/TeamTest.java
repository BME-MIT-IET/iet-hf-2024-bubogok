import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import temalab.common.MainModel;
import temalab.model.Field;
import temalab.model.Position;
import temalab.model.Team;
import temalab.model.Unit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamTest {
	private Team team;

	@Mock
	private MainModel mockMainModel;
	@Mock
	private Unit mockUnit;
	@Mock
	private Position mockPos;
	@Mock
	private Field mockField;

	@BeforeEach
	public void init() {
		this.team = new Team("red", "", 100, mockMainModel);
	}

	@Test
	void test_addUnit_canAfford() {
		when(mockUnit.price()).thenReturn(40);
		this.team.addUnit(mockUnit);
		Assertions.assertFalse(this.team.units().isEmpty());
	}

	@Test
	void test_addUnit_canNotAfford() {
		when(mockUnit.price()).thenReturn(120);
		this.team.addUnit(mockUnit);
		Assertions.assertTrue(this.team.units().isEmpty());
	}

	void addMockUnitWithId(int id) {
		when(mockUnit.getUUID()).thenReturn(id);
		when(mockUnit.price()).thenReturn(40);
		this.team.addUnit((mockUnit));
	}

	@Test
	void test_moveUnit_OK() {
		addMockUnitWithId(1);

		when(mockMainModel.getField(mockPos)).thenReturn(mockField);
		this.team.moveUnit(1, mockPos);
		verify(mockUnit, times(1)).move(mockField);
	}

	@Test
	void test_moveUnit_fieldMissing() {
		addMockUnitWithId(1);

		when(mockMainModel.getField(mockPos)).thenReturn(null);
		this.team.moveUnit(1, mockPos);
		verify(mockUnit, times(0)).move(mockField);
	}

	@Test
	void test_moveUnit_unitMissing() {
		addMockUnitWithId(1);

		when(mockMainModel.getField(mockPos)).thenReturn(mockField);
		this.team.moveUnit(2, mockPos);
		verify(mockUnit, times(0)).move(mockField);
	}

	@Test
	void test_fireUnit_OK() {
		addMockUnitWithId(1);

		when(mockMainModel.getField(any(Position.class))).thenReturn(mockField);
		this.team.fireUnit(1, mockPos);
		verify(mockUnit, times(1)).shoot(mockField);
	}

	@Test
	void test_fireUnit_fieldMissing() {
		addMockUnitWithId(1);

		when(mockMainModel.getField(any(Position.class))).thenReturn(null);
		this.team.fireUnit(1, mockPos);
		verify(mockUnit, times(0)).shoot(mockField);
	}

	@Test
	void test_fireUnit_unitMissing() {
		addMockUnitWithId(1);

		when(mockMainModel.getField(any(Position.class))).thenReturn(mockField);
		this.team.fireUnit(2, mockPos);
		verify(mockUnit, times(0)).shoot(mockField);
	}
}