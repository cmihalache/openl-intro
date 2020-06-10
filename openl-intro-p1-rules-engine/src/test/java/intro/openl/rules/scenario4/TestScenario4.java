package intro.openl.rules.scenario4;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openl.rules.runtime.RulesEngineFactory;

import intro.openl.rules.scenario4.model.CarBodyType;

public class TestScenario4 {
	private static final String EXCEL_FILE = "src/test/resources/Scenario4.xlsx";

	@Test
	void testPremiumAllowedRule() throws Exception {
		RulesEngineFactory<MyRules> rulesFactory = new RulesEngineFactory<>(EXCEL_FILE, MyRules.class);
		MyRules rules = rulesFactory.newEngineInstance();
		assertFalse(rules.isPremiumAllowed(26, 10000, CarBodyType.COUPE));
		assertTrue(rules.isPremiumAllowed(25, 10000, CarBodyType.COUPE));
		assertTrue(rules.isPremiumAllowed(5, 5000, CarBodyType.SUV));
		assertFalse(rules.isPremiumAllowed(5, 4999, CarBodyType.SUV));
		assertTrue(rules.isPremiumAllowed(5, 4500, CarBodyType.CONVERTIBLE));
		assertFalse(rules.isPremiumAllowed(5, 4499, CarBodyType.CONVERTIBLE));
		assertTrue(rules.isPremiumAllowed(5, 4000, CarBodyType.SEDAN));
		assertFalse(rules.isPremiumAllowed(5, 3999, CarBodyType.SEDAN));
	}

	private static interface MyRules {
		boolean isPremiumAllowed(int vehicleAge, int currentVehicleMarketValue, CarBodyType vehicleBody);
	}

}
