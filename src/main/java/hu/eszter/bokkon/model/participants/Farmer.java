package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.*;

public class Farmer implements MoveAnimal {

    private final String name;
    private final Random random = new Random();
    private Map<Animal, Integer> farmerLiveStock = new HashMap<>();

    public Farmer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<Animal, Integer> getFarmerLiveStock() {
        return farmerLiveStock;
    }

    @Override
    public void addAnimal(Animal animal) {
        farmerLiveStock.putIfAbsent(animal, 0);
        farmerLiveStock.put(animal, farmerLiveStock.get(animal) + 1);
    }

    @Override
    public void addAnimals(List<Animal> animals) {
        Animal animal = animals.get(0);
        farmerLiveStock.putIfAbsent(animal, 0);
        farmerLiveStock.put(animal, farmerLiveStock.get(animal) + animals.size());
    }

    @Override
    public void removeAnimal(Animal animal) {
        if (farmerLiveStock.get(animal) > 0) {
            farmerLiveStock.put(animal, farmerLiveStock.get(animal) - 1);
        }
        if (farmerLiveStock.get(animal) == 0) {
            farmerLiveStock.remove(animal);
        }
    }

    @Override
    public void removeAnimals(List<Animal> animals) {
        Animal animal = animals.get(0);
        if (farmerLiveStock.get(animal) >= animals.size()) {
            farmerLiveStock.put(animal, farmerLiveStock.get(animal) - animals.size());
        }
        if (farmerLiveStock.get(animal) == 0) {
            farmerLiveStock.remove(animal);
        }
    }

    //TODO
    public void change() {}

    public Animal rollDice(Dice dice) {
        int randomNo = random.nextInt(12);
        return dice.getDiceSides().get(randomNo);
    }

}
