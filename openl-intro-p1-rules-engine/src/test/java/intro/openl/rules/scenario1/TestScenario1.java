package intro.openl.rules.scenario1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openl.rules.runtime.RulesEngineFactory;

public class TestScenario1 {
	private static final String EXCEL_FILE = "src/test/resources/Scenario1.xlsx";

	@Test
	void testPremiumAllowedRule() throws Exception {
		RulesEngineFactory<MyRules> rulesFactory = new RulesEngineFactory<>(EXCEL_FILE, MyRules.class);
		MyRules rules = rulesFactory.newEngineInstance();
		assertFalse(rules.isPremiumAllowed(26, 10000));
		assertTrue(rules.isPremiumAllowed(25, 10000));
		assertTrue(rules.isPremiumAllowed(5, 5000));
		assertFalse(rules.isPremiumAllowed(5, 4999));
	}

	private static interface MyRules {
		boolean isPremiumAllowed(int vehicleAge, int currentVehicleMarketValue);
	}

}
