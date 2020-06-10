package intro.openl.myrules.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import intro.openl.myrules.model.MyRules;
import intro.openl.myrules.model.Owner;
import intro.openl.myrules.model.Platform;
import intro.openl.myrules.model.Vehicle;
import intro.openl.myrules.model.VehicleBodyType;

@RestController
public class MyRulesController {

	private final MyRules myRules;
	private final Platform platform;
	private final OwnerRepository ownerRepository;

	public MyRulesController(MyRules myRules, Platform platform, OwnerRepository ownerRepository) {
		this.myRules = myRules;
		this.platform = platform;
		this.ownerRepository = ownerRepository;
	}

	@GetMapping(path = "/premimumAllowed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getGreeting(@RequestParam int customerId, @RequestParam int vehicleMfgYear,
			@RequestParam int vehicleValue, @RequestParam VehicleBodyType vehicleBodyType) {
		boolean allowed = false;
		Optional<Owner> ownerOptional = ownerRepository.find(customerId);

		if(ownerOptional.isPresent()) {
			Vehicle vehicle = new Vehicle(vehicleMfgYear, vehicleValue, vehicleBodyType);
			allowed = myRules.isPremiumAllowed(vehicle, ownerOptional.get(), platform);
		}

		return new ResponseEntity<>("{\"response\" : \"" + allowed + "\"}", HttpStatus.OK);
	}

}
