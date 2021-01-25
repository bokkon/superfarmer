package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.List;
import java.util.Random;

public class Farmer implements CollectAnimal{

    private final String name;
    private List<Animal> farmerAnimals;
    private Random random;

    public Farmer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Animal> getFarmerAnimals() {
        return farmerAnimals;
    }

    public boolean hasAnimal(Animal animal) {
        return farmerAnimals.contains(animal);
    }

    @Override
    public void addAnimal(Animal newAnimal) {
        farmerAnimals.add(newAnimal);
    }

    @Override
    public void addAnimals(List<Animal> newAnimals) {
        farmerAnimals.addAll(newAnimals);
    }

    //TODO
    public void change() {}

    public String rollDice12(Dice dice) {
        int randomNo = random.nextInt(12) + 1;
        return dice.getDiceSides().get(randomNo);
    }
}
