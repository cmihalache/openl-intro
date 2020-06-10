package intro.openl.myrules.services;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import intro.openl.myrules.model.VehicleBodyType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Disabled("Because it needs the database to work. Use DeployUtils to create the database")
public class MyRulesApplicationTests {

	@Autowired
	private WebTestClient client;

	@Test
	public void testPremiumAllowed() {
		checkPremimAllowed(false, 0, 1994, 10000, VehicleBodyType.COUPE);
		checkPremimAllowed(true, 0, 1995, 10000, VehicleBodyType.COUPE);
		checkPremimAllowed(true, 0, 2015, 5000, VehicleBodyType.SUV);
		checkPremimAllowed(false, 0, 2015, 4999, VehicleBodyType.SUV);
		checkPremimAllowed(true, 0, 2015, 4500, VehicleBodyType.CONVERTIBLE);
		checkPremimAllowed(false, 0, 2015, 4499, VehicleBodyType.CONVERTIBLE);
		checkPremimAllowed(true, 0, 2015, 4000, VehicleBodyType.SEDAN);
		checkPremimAllowed(false, 0, 2015, 3999, VehicleBodyType.SEDAN);
	}

	private void checkPremimAllowed(Boolean expected, int customerId, int vehicleMfgYear, int vehicleValue,
			VehicleBodyType vehicleBodyType) {
		client.get()
				.uri(uriBuilder -> uriBuilder.path("/premimumAllowed").queryParam("customerId", customerId)
						.queryParam("vehicleMfgYear", vehicleMfgYear).queryParam("vehicleValue", vehicleValue)
						.queryParam("vehicleBodyType", vehicleBodyType).build())
				.accept(APPLICATION_JSON).exchange().expectStatus().isOk().expectHeader().contentType(APPLICATION_JSON)
				.expectBody().jsonPath("$.response").isEqualTo(expected.toString());
	}

}
