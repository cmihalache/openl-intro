package intro.openl.rules.scenario5.model;

public class Vehicle {
	private int age;
	private int currentValue;
	private VehicleBodyType bodyType;

	public Vehicle() {
		// Default constructor
	}

	public Vehicle(int age, int currentValue, VehicleBodyType bodyType) {
		this.age = age;
		this.currentValue = currentValue;
		this.bodyType = bodyType;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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
