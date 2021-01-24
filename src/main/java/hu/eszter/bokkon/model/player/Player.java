package hu.eszter.bokkon.model.player;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.Set;

public class Player {

    private final String name;
    private Set<Animal> playerAnimals;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Animal> getPlayerAnimals() {
        return playerAnimals;
    }

    public void addAnimal(Animal newAnimal) {
        playerAnimals.add(newAnimal);
    }

    public void addAnimals(Set<Animal> newAnimals) {
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
