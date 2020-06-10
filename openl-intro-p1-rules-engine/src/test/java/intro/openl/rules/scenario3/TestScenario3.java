package intro.openl.rules.scenario3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openl.binding.impl.cast.OutsideOfValidDomainException;
import org.openl.rules.runtime.RulesEngineFactory;

public class TestScenario3 {
	private static final String EXCEL_FILE = "src/test/resources/Scenario3.xlsx";

	@Test
	void testPremiumAllowedRule() throws Exception {
		RulesEngineFactory<MyRules> rulesFactory = new RulesEngineFactory<>(EXCEL_FILE, MyRules.class);
		MyRules rules = rulesFactory.newEngineInstance();
		assertFalse(rules.isPremiumAllowed(26, 10000, "Coupe"));
		assertTrue(rules.isPremiumAllowed(25, 10000, "Coupe"));
		assertTrue(rules.isPremiumAllowed(5, 5000, "SUV"));
		assertFalse(rules.isPremiumAllowed(5, 4999, "SUV"));
		assertTrue(rules.isPremiumAllowed(5, 4500, "Convertible"));
		assertFalse(rules.isPremiumAllowed(5, 4499, "Convertible"));
		assertTrue(rules.isPremiumAllowed(5, 4000, "Sedan"));
		assertFalse(rules.isPremiumAllowed(5, 3999, "Sedan"));
		OutsideOfValidDomainException e = assertThrows(OutsideOfValidDomainException.class,
				() -> rules.isPremiumAllowed(5, 5000, "Any"));
		String expectedExceptionMessage = "Object 'Any' is outside of valid domain 'CarBodyType'. "
				+ "Valid values: [Convertible, Coupe, Hatchback, Jeep, Sedan, SUV, Van, Wagon]";
		assertEquals(expectedExceptionMessage, e.getMessage());
	}

	private static interface MyRules {
		boolean isPremiumAllowed(int vehicleAge, int currentVehicleMarketValue, String vehicleBody);
	}

}
