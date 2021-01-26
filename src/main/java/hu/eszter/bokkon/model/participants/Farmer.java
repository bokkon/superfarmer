package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.*;

public class Farmer implements MoveAnimal {

    private final String name;
    private final Random random = new Random();
    private Map<String, Integer> farmerLiveStock = new HashMap<>();
    private Integer numberOfSmallDogs = 0;
    private Integer numberOfBigDogs = 0;

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
    public void addAnimal(Animal animal) {
        String name = animal.getClass().getSimpleName();
        farmerLiveStock.putIfAbsent(name, 0);
        farmerLiveStock.put(name, farmerLiveStock.get(name) + 1);
    }

    @Override
    public void addAnimals(List<Animal> animals) {
        String name = animals.get(0).getClass().getSimpleName();
        farmerLiveStock.putIfAbsent(name, 0);
        farmerLiveStock.put(name, farmerLiveStock.get(name) + animals.size());
    }

    @Override
    public void removeAnimal(Animal animal) {
        String name = animal.getClass().getSimpleName();
        if (farmerLiveStock.get(name) >= 0) {
            farmerLiveStock.replace(name, farmerLiveStock.get(name) - 1);
        }
        if (farmerLiveStock.get(name) == 0) {
            farmerLiveStock.remove(name);
        }
    }

    @Override
    public void removeAnimals(List<Animal> animals) {
        String name = animals.get(0).getClass().getSimpleName();
        if (farmerLiveStock.get(name) >= animals.size()) {
            farmerLiveStock.replace(name, farmerLiveStock.get(name) - animals.size());
        }
        if (farmerLiveStock.get(name) == 0) {
            farmerLiveStock.remove(name);
        }
    }

    public Integer getNumberOfSmallDogs() {
        return numberOfSmallDogs;
    }

    public void setNumberOfSmallDogs(Integer numberOfSmallDogs) {
        this.numberOfSmallDogs = numberOfSmallDogs;
    }

    public Integer getNumberOfBigDogs() {
        return numberOfBigDogs;
    }

    public void setNumberOfBigDogs(Integer numberOfBigDogs) {
        this.numberOfBigDogs = numberOfBigDogs;
    }

    //TODO
    public void change() {}

    public String rollDice(Dice dice) {
        int randomNo = random.nextInt(12);
        return dice.getDiceSides().get(randomNo);
    }

}
