package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.*;

public class Farmer implements MoveAnimal {

    private final String name;
    private final Random random = new Random();
    private Map<String, Integer> farmerLiveStock = new HashMap<>();

    public Farmer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getFarmerLiveStock() {
        return farmerLiveStock;
    }

    @Override
    public void addAnimal(String animal) {
        farmerLiveStock.putIfAbsent(animal, 0);
        farmerLiveStock.put(animal, farmerLiveStock.get(animal) + 1);
    }

    @Override
    public void addAnimals(List<String> animals) {
        for (String actAnimal: animals) {
            addAnimal(actAnimal);
        }
    }

    @Override
    public void removeAnimal(String animal) {
        if (farmerLiveStock.get(animal) > 0) {
            farmerLiveStock.put(animal, farmerLiveStock.get(animal) - 1);
        }
        if (farmerLiveStock.get(animal) == 0) {
            farmerLiveStock.remove(animal);
        }
    }

    @Override
    public void removeAnimals(List<String> animals) {
        for (String actAnimal: animals) {
            removeAnimal(actAnimal);
        }
    }

    //TODO
    public void change(Map<String, String> actPossibleChanges) {}

    public String rollDice(Dice dice) {
        int randomNo = random.nextInt(12);
        return dice.getDiceSides().get(randomNo);
    }

}
