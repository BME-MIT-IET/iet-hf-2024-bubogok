package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assume;
import temalab.common.MainModel;
import temalab.common.map_generation.AllGreenMapGeneratorStrategy;
import temalab.model.Position;
import temalab.model.Team;
import temalab.model.Unit;
import temalab.model.Unit.Type;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CucumberStepDefinitions {
	MainModel mainModel;
	Map<String, Team> teams = new HashMap<>();
	Map<String, Unit> units = new HashMap<>();

	@Given("the map size is {int}")
	public void theMapSizeIs(int mapSize) {
		mainModel = new MainModel(mapSize, new AllGreenMapGeneratorStrategy());
	}

	@Given("^a (.*) (.*) on (\\d*), (\\d*)$")
	public void there_is_a(String teamName, String unitType, int x, int y) {
		Team team;
		if (teams.containsKey(teamName)) {
			team = teams.get(teamName);
		} else {
			team = new Team(teamName, "", 0, mainModel);
		}

		Unit unit = new Unit(mainModel.getField(new Position(x, y)), team, Type.valueOf(unitType.toUpperCase()));
		units.put(teamName + " " + unitType, unit);
	}

	@When("^the (.*) shoots the (.*)$")
	public void theShootsThe(String shooterName, String shotName) {
		Unit shooter = units.get(shooterName);
		Unit shot = units.get(shotName);
		if (shooter.getAmmo() > 0) {
			shooter.shoot(shot.getField());
		} else {
			assertThrows(Exception.class, () -> shooter.shoot(shot.getField()));
		}
	}

	@Then("^the (.*) is (\\d*) below full health$")
	public void theIsBelowFullHealth(String unitName, int missingHealth) {
		Unit unit = units.get(unitName);
		int currentHealth = unit.getHealth();
		int maxHealth = unit.getMaxHealth();
		assertEquals(maxHealth - missingHealth, currentHealth);
	}

	@Then("^the (.*) dies$")
	public void theDies(String unitName) {
		Unit unit = units.get(unitName);
		assertFalse(isUnitAlive(unit));
	}

	@Then("^the (.*) stays alive$")
	public void theStaysAlive(String unitName) {
		Unit unit = units.get(unitName);
		assertTrue(isUnitAlive(unit));
	}

	private boolean isUnitAlive(Unit unit) {
		boolean alive = unit.getTeam().getUnits().containsValue(unit);
		boolean notDead = !unit.getTeam().getDeadUnits().containsValue(unit);
		Assume.assumeTrue(alive == notDead);
		return alive;
	}


	@Given("^the (.*) has (\\d*) action points$")
	public void theHasActionPoints(String unitName, int actionPoints) {
		Unit unit = units.get(unitName);
		unit.setActionPoints(actionPoints);
	}

	@Then("^the (.*) has (\\d*) action points left$")
	public void theHasActionPointsLeft(String unitName, int actionPoints) {
		Unit unit = units.get(unitName);
		assertEquals(actionPoints, unit.getActionPoints());
	}

	@Given("^the (.*) has (\\d*) ammo$")
	public void theHasAmmo(String unitName, int ammo) {
		Unit unit = units.get(unitName);
		unit.setAmmo(ammo);
	}

	@Then("^the (.*) has (\\d*) ammo left$")
	public void thenTheHasAmmoLeft(String unitName, int ammo) {
		Unit unit = units.get(unitName);
		assertEquals(ammo, unit.getAmmo());
	}

	@When("^the (.*) tries to move to (-?\\d*), (-?\\d*)$")
	public void theRedTankTriesToMoveTo(String unitName, int x, int y) {
		Unit unit = units.get(unitName);
		if (unit.actionPoints() > 0 && mainModel.getField(new Position(x, y)) != null
				&& Math.abs(unit.getField().pos().x() - x) <= 1
				&& Math.abs(unit.getField().pos().y() - y) <= 1) {
			unit.move(mainModel.getField(new Position(x, y)));
		} else {
			assertThrows(Exception.class, () -> unit.move(mainModel.getField(new Position(x, y))));
		}
	}

	@Then("^the (.*) is on (\\d*), (\\d*)$")
	public void theIsOn(String unitName, int x, int y) {
		Unit unit = units.get(unitName);
		assertEquals(mainModel.getField(new Position(x, y)), unit.getField());

	}
}
