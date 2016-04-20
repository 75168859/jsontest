package cn.comm;

public class Animal {
	private String animalName;
	private String animalColor;

	public Animal() {
	}

	public Animal(String animalName, String animalColor) {
		this.animalName = animalName;
		this.animalColor = animalColor;
	}

	public String getAnimalName() {
		return animalName;
	}

	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}

	public String getAnimalColor() {
		return animalColor;
	}

	public void setAnimalColor(String animalColor) {
		this.animalColor = animalColor;
	}

}
