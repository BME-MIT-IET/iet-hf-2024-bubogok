// package cucumber;

// import io.cucumber.java.Before;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.Then;
// import io.cucumber.java.en.When;
// import org.junit.Assert;
// import temalab.common.MainModel;
// import temalab.common.map_generation.AllGreenMapGeneratorStrategy;
// import temalab.model.Field;
// import temalab.model.Position;
// import temalab.model.Team;
// import temalab.model.Unit;

// public class CucumberStepDefinitions {
// 	MainModel mainModel = new MainModel(3, new AllGreenMapGeneratorStrategy());
// 	Field infantryField;
// 	Field tankField;
// 	Team red;
// 	Team white;
// 	Unit infantry;
// 	Unit tank;

// 	@Before
// 	public void setup() {
// 		red = new Team("red", null, 0, mainModel);
// 		white = new Team("white", null, 0, mainModel);
// 	}

// 	@Given("the infantry is on the field")
// 	public void theInfantryIsOnTheField() {
// 		infantryField = mainModel.getField(new Position(0, 0));
// 		infantry = new Unit(infantryField, red, Unit.Type.INFANTRY);
// 	}

// 	@When("a tank shoots on the field")
// 	public void aTankShootsOnTheField() {
// 		tankField = mainModel.getField(new Position(0, 1));
// 		tank = new Unit(infantryField, white, Unit.Type.TANK);
// 		tank.shoot(infantryField);
// 	}

// 	@Then("the infantry takes {int} damage")
// 	public void theInfantryTakesDamage(int damage) {
// 		Assert.assertTrue(infantry.getMaxHealth() >= damage ? infantry.getHealth() == infantry.getMaxHealth() - damage : infantry.getHealth() <= 0 && infantryField.getUnit() == null);
// 	}

// 	@Given("nothing")
// 	public void _nothing() {
// 	}

// 	@When("nothing happens")
// 	public void nothing() {
// 	}

// 	@Then("true")
// 	public void _true() {
// 		Assert.assertTrue(true);
// 	}
// }
