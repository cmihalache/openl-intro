package intro.openl.myrules.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import intro.openl.myrules.model.Owner;

public class OwnerRepository {
	private List<Owner> owners = new ArrayList<>();

	public OwnerRepository() {
		owners.add(createOwner("John", "Max", "john@x.com", "141 Blackborough Rd", "Reigate", "Surrey", "RH2 7DA"));
		owners.add(createOwner("Mike", "Black", "mike@x.com", "40 Springwell Rd", "Tonbridge", "Kent", "TN9 2LN"));
	}

	public Optional<Owner> find(int ownerId) {
		if (ownerId >= 0 && ownerId < owners.size()) {
			return Optional.of(owners.get(ownerId));
		}
		return Optional.empty();
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

}
