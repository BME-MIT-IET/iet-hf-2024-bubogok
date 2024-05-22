import com.google.errorprone.annotations.DoNotMock;

import temalab.common.MainModel;
import temalab.model.Field;
import temalab.model.Position;
import temalab.model.Team;
import temalab.model.Unit;
import temalab.model.Field.Type;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FieldTest {
    
    private Field field;

    @Mock
    private Field otherField;
    @Mock
	private MainModel mockMainModel;
    @Mock
    private Unit unit;

    @BeforeEach
    public void init(){
        field = new Field(new Position(0,0), Type.GRASS);
        // otherField = new Field(new Position(0,1), Type.GRASS);

        // Team team = new Team("TestTeam", "null", 10, mockMainModel);
        //unit = new Unit(otherField, team, Unit.Type.TANK);
    }

    @Test
    public void unitArrivesOnFieldSuccess(){
        assertTrue(field.arrive(unit));
    }

    @Test
    public void unitTakesShot(){
        when(unit.getHealth()).thenReturn(1).thenReturn(0);
        int unitDefaultHealth = unit.getHealth();
        field.takeShot(1);
        assertEquals(unitDefaultHealth-1, unit.getHealth());
        verify(unit, times(1)).takeShot(1);
    }

    @Test
    public void fieldsNeighbouring(){
        assertTrue(field.isNeighbouring(otherField));
    }

    @Test
    public void fieldIsInDistance(){
        assertTrue(field.inDistance(otherField, 1));
    }

}
