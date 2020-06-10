package intro.openl.rules.scenario7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openl.rules.runtime.RulesEngineFactory;

import intro.openl.rules.scenario7.model.Owner;
import intro.openl.rules.scenario7.model.Platform;
import intro.openl.rules.scenario7.model.Vehicle;
import intro.openl.rules.scenario7.model.VehicleBodyType;

public class TestScenario7 {
	private static final String EXCEL_FILE_PATH = "src/test/resources/";

	@ParameterizedTest
	@ValueSource(strings = { "Scenario7.xlsx", "Scenario9.xlsx" })
	void testPremiumAllowedRule(String excelFileName) throws Exception {
		RulesEngineFactory<MyRules> rulesFactory = new RulesEngineFactory<>(EXCEL_FILE_PATH + excelFileName,
				MyRules.class);
		MyRules rules = rulesFactory.newEngineInstance();
		TestPlatform p = new TestPlatform();
		verifyDecisions(rules, p);
		verifyNotifications(rules, p);
	}

	private void verifyDecisions(MyRules rules, TestPlatform p) {
		Owner oSurrey = createOwner("John", "Max", "john@x.com", "141 Blackborough Rd", "Reigate", "Surrey", "RH2 7DA");
		assertFalse(rules.isPremiumAllowed(new Vehicle(1994, 10000, VehicleBodyType.COUPE), oSurrey, p));
		assertTrue(rules.isPremiumAllowed(new Vehicle(1995, 10000, VehicleBodyType.COUPE), oSurrey, p));
		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 5000, VehicleBodyType.SUV), oSurrey, p));
		assertFalse(rules.isPremiumAllowed(new Vehicle(2015, 4999, VehicleBodyType.SUV), oSurrey, p));
		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 4500, VehicleBodyType.CONVERTIBLE), oSurrey, p));
		assertFalse(rules.isPremiumAllowed(new Vehicle(2015, 4499, VehicleBodyType.CONVERTIBLE), oSurrey, p));
		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 4000, VehicleBodyType.SEDAN), oSurrey, p));
		assertFalse(rules.isPremiumAllowed(new Vehicle(2015, 3999, VehicleBodyType.SEDAN), oSurrey, p));
	}

	private void verifyNotifications(MyRules rules, TestPlatform p) {
		Owner oSurrey = createOwner("John", "Max", "john@x.com", "141 Blackborough Rd", "Reigate", "Surrey", "RH2 7DA");
		verifyNonSurreyOwnerDowsNotGenerateNotifications(rules, p);

		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 49999, VehicleBodyType.SUV), oSurrey, p));
		assertTrue(p.getNotifications().isEmpty());

		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 50000, VehicleBodyType.SUV), oSurrey, p));
		assertEquals(1, p.getNotifications().size());
		assertNotification(p.getNotifications().get(0), "3575", "Max,john@x.com");

		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 100000, VehicleBodyType.SUV), oSurrey, p));
		assertEquals(2, p.getNotifications().size());
		assertNotification(p.getNotifications().get(1), "3575", "Max,john@x.com");

		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 100001, VehicleBodyType.SUV), oSurrey, p));
		assertEquals(3, p.getNotifications().size());
		assertNotification(p.getNotifications().get(2), "3576", "Max,john@x.com");
	}

	private void verifyNonSurreyOwnerDowsNotGenerateNotifications(MyRules rules, TestPlatform p) {
		Owner oKent = createOwner("Mike", "Black", "mike@x.com", "40 Springwell Rd", "Tonbridge", "Kent", "TN9 2LN");
		assertTrue(p.getNotifications().isEmpty());
		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 49999, VehicleBodyType.SUV), oKent, p));
		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 50000, VehicleBodyType.SUV), oKent, p));
		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 100000, VehicleBodyType.SUV), oKent, p));
		assertTrue(rules.isPremiumAllowed(new Vehicle(2015, 100001, VehicleBodyType.SUV), oKent, p));
		assertTrue(p.getNotifications().isEmpty());
	}

	private void assertNotification(Notification notification, String expectedCode, String expectedMessage) {
		assertEquals(expectedCode, notification.code);
		assertEquals(expectedMessage, notification.content);
	}

	private Owner createOwner(String firstName, String lastName, String mail, String address, String city,
			String county, String postalCode) {
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setMail(mail);
		owner.setAddress(address);
		owner.setCity(city);
		owner.setCounty(county);
		owner.setPostalCode(postalCode);
		return owner;
	}

	private static interface MyRules {
		boolean isPremiumAllowed(Vehicle vehicle, Owner owner, Platform platform);
	}

	private static class TestPlatform implements Platform {
		private List<Notification> notifications = new ArrayList<>();

		@Override
		public void sendNotification(String code, String content) {
			notifications.add(new Notification(code, content));
		}

		public List<Notification> getNotifications() {
			return Collections.unmodifiableList(notifications);
		}
	}

	private static class Notification {
		final String code;
		final String content;

		Notification(String code, String content) {
			this.code = code;
			this.content = content;
		}
	}

}
