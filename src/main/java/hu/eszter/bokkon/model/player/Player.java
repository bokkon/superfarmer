package hu.eszter.bokkon.model.player;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private List<Animal> playerAnimals = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Animal> getPlayerAnimals() {
        return playerAnimals;
    }

    public void addAnimal(Animal newAnimal) {
        playerAnimals.add(newAnimal);
    }

    public void addAnimals(List<Animal> newAnimals) {
        playerAnimals.addAll(newAnimals);
    }

    public boolean hasAnimal(Animal animal) {
        return playerAnimals.contains(animal);
    }

    //TODO
    public void change() {}

    //TODO
    public void rollDice() {}
}
