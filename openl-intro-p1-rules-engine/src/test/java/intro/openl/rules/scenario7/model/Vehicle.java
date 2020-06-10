package intro.openl.rules.scenario7.model;

public class Vehicle {
	private int manufactureYear;
	private int currentValue;
	private VehicleBodyType bodyType;

	public Vehicle() {
		// Default constructor
	}

	public Vehicle(int manufactureYear, int currentValue, VehicleBodyType bodyType) {
		this.manufactureYear = manufactureYear;
		this.currentValue = currentValue;
		this.bodyType = bodyType;
	}

	public int getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(int manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	public VehicleBodyType getBodyType() {
		return bodyType;
	}

	public void setBodyType(VehicleBodyType bodyType) {
		this.bodyType = bodyType;
	}

}
