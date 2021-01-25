package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Farmer implements CollectAnimal{

    private final String name;
    private final Random random = new Random();
    private List<Animal> farmerAnimals = new ArrayList<>();

    public Farmer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Animal> getFarmerAnimals() {
        return farmerAnimals;
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
    public void checkChangePossibilities() { }

    //TODO
    public void change() {}

    public String rollDice(Dice dice) {
        int randomNo = random.nextInt(12);
        return dice.getDiceSides().get(randomNo);
    }

    //TODO
    public void evaluateDiceResult(String result1, String result2) {
    }
}
