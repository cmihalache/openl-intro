package intro.openl.rules.scenario5;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openl.rules.runtime.RulesEngineFactory;

import intro.openl.rules.scenario5.model.Vehicle;
import intro.openl.rules.scenario5.model.VehicleBodyType;

public class TestScenario5 {
	private static final String EXCEL_FILE = "src/test/resources/Scenario5.xlsx";

	@Test
	void testPremiumAllowedRule() throws Exception {
		RulesEngineFactory<MyRules> rulesFactory = new RulesEngineFactory<>(EXCEL_FILE, MyRules.class);
		MyRules rules = rulesFactory.newEngineInstance();
		assertFalse(rules.isPremiumAllowed(new Vehicle(26, 10000, VehicleBodyType.COUPE)));
		assertTrue(rules.isPremiumAllowed(new Vehicle(25, 10000, VehicleBodyType.COUPE)));
		assertTrue(rules.isPremiumAllowed(new Vehicle(5, 5000, VehicleBodyType.SUV)));
		assertFalse(rules.isPremiumAllowed(new Vehicle(5, 4999, VehicleBodyType.SUV)));
		assertTrue(rules.isPremiumAllowed(new Vehicle(5, 4500, VehicleBodyType.CONVERTIBLE)));
		assertFalse(rules.isPremiumAllowed(new Vehicle(5, 4499, VehicleBodyType.CONVERTIBLE)));
		assertTrue(rules.isPremiumAllowed(new Vehicle(5, 4000, VehicleBodyType.SEDAN)));
		assertFalse(rules.isPremiumAllowed(new Vehicle(5, 3999, VehicleBodyType.SEDAN)));
	}

	private static interface MyRules {
		boolean isPremiumAllowed(Vehicle vehicle);
	}

}
