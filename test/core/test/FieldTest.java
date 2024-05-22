import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.errorprone.annotations.DoNotMock;

import temalab.common.MainModel;
import temalab.model.Field;
import temalab.model.Position;
import temalab.model.Team;
import temalab.model.Unit;
import temalab.model.Field.Type;

public class FieldTest {
    @Mock
    Field field;
    @Mock
    Field otherField;
    @Mock
	private MainModel mockMainModel;
    @Mock
    Unit unit;

    @BeforeEach
    public void init(){
        field = new Field(new Position(0,0), Type.GRASS);
        otherField = new Field(new Position(0,1), Type.GRASS);

        Team team = new Team("TestTeam", "null", 10, null);
        unit = new Unit(otherField, team, Unit.Type.TANK);
    }

    @Test
    public void unitArrivesOnFieldSuccess(){
        Assert.assertTrue(field.arrive(unit));
    }

    @Test
    public void unitTakesShot(){
        int unitDefaultHealth = unit.getHealth();
        field.takeShot(1);
        Assert.assertEquals(unitDefaultHealth-1, unit.getHealth());
    }

    @Test
    public void fieldsNeighbouring(){
        Assert.assertTrue(field.isNeighbouring(otherField));
    }

    @Test
    public void fieldIsInDistance(){
        Assert.assertTrue(field.inDistance(otherField, 1));
    }

}
