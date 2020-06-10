package intro.openl.rules.scenario2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openl.rules.runtime.RulesEngineFactory;

public class TestScenario2 {
	private static final String EXCEL_FILE = "src/test/resources/Scenario2.xlsx";

	@Test
	void testPremiumAllowedRule() throws Exception {
		RulesEngineFactory<MyRules> rulesFactory = new RulesEngineFactory<>(EXCEL_FILE, MyRules.class);
		MyRules rules = rulesFactory.newEngineInstance();
		assertFalse(rules.isPremiumAllowed(26, 10000, "Any"));
		assertTrue(rules.isPremiumAllowed(25, 10000, "Any"));
		assertTrue(rules.isPremiumAllowed(5, 5000, "SUV"));
		assertFalse(rules.isPremiumAllowed(5, 4999, "SUV"));
		assertTrue(rules.isPremiumAllowed(5, 4500, "Convertible"));
		assertFalse(rules.isPremiumAllowed(5, 4499, "Convertible"));
		assertTrue(rules.isPremiumAllowed(5, 4000, "Any"));
		assertFalse(rules.isPremiumAllowed(5, 3999, "Any"));
	}

	private static interface MyRules {
		boolean isPremiumAllowed(int vehicleAge, int currentVehicleMarketValue, String vehicleBody);
	}

}
