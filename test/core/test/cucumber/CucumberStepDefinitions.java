package cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import temalab.model.Field;
import temalab.model.Position;
import temalab.model.Team;
import temalab.model.Unit;

public class CucumberStepDefinitions {
	Field infantryField;
	Field tankField;
	Team red;
	Team white;
	Unit infantry;
	Unit tank;

	@Before
	public void setup() {
		red = new Team("red", null, 0, null);
		white = new Team("white", null, 0, null);
	}

	@Given("the infantry is on the field")
	public void theInfantryIsOnTheField() {
		infantryField = new Field(new Position(0, 0), Field.Type.GRASS);
		infantry = new Unit(infantryField, red, Unit.Type.INFANTRY);
	}

	@When("a tank shoots on the field")
	public void aTankShootsOnTheField() {
		tankField = new Field(new Position(1, 0), Field.Type.GRASS);
		tank = new Unit(infantryField, white, Unit.Type.TANK);
		tank.shoot(infantryField);
	}

	@Then("the infantry takes {int} damage")
	public void theInfantryTakesDamage(int damage) {
		Assert.assertTrue(infantry.getMaxHealth() >= damage ? infantry.getHealth() == infantry.getMaxHealth() - damage : infantry.getHealth() <= 0 && infantryField.getUnit() == null);
	}
}
