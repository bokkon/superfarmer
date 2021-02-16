package hu.eszter.bokkon.model.animal;

public enum Animal {

    RABBIT(AnimalType.NORMAL),
    SHEEP(AnimalType.NORMAL),
    PIG(AnimalType.NORMAL),
    COW(AnimalType.NORMAL),
    HORSE(AnimalType.NORMAL),
    SMALLDOG(AnimalType.PROTECTOR),
    BIGDOG(AnimalType.PROTECTOR),
    FOX(AnimalType.ATTACKER),
    WOLF(AnimalType.ATTACKER);

    private final AnimalType animalType;

    Animal(AnimalType animalType) {
        this.animalType = animalType;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }
}
