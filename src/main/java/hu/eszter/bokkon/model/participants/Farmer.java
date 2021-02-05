package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.*;

/**
 * Farmer is the player, who can collect animals. 2-4 players can play in 1 game.
 */
public class Farmer implements MoveAnimal {

    private final String name;
    private final Random random = new Random();
    private Map<Animal, Integer> farmerLiveStock = new LinkedHashMap<>();
    private Map<Animal, Map<Animal, Double>> actualPossibleChanges = new LinkedHashMap<>();

    public Farmer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<Animal, Integer> getFarmerLiveStock() {
        return farmerLiveStock;
    }

    /**
     * Implementation of addAnimals method of MoveAnimal interface. Adds given number of the same type of animal to
     * the animal stock of the farmer(player), which is an empty map by default. If the the stock doesn't contain the
     * type of animal yet, it adds it to the stock.
     *
     * @param animal  type of animal to be added
     * @param howMany number of the same type of animal to be added
     */
    @Override
    public void addAnimals(Animal animal, int howMany) {
        if (1 <= howMany) {
            farmerLiveStock.putIfAbsent(animal, 0);
            farmerLiveStock.computeIfPresent(animal, (k, v) -> v + howMany);
        } else {
            System.out.println("No animal was added to " + name + "'s stock.");
        }
    }

    /**
     * Implementation of removeAnimals method of MoveAnimal interface. Removes given number of the same type of animal
     * from the animal stock of the farmer(player) if there are available number of animals in the stock, which is an
     * empty map by default. In case the number of animals becomes 0, it removes the tape of animal from the stock.
     *
     * @param animal  type of animal to be removed
     * @param howMany number of the same type of animal to be removed
     */
    @Override
    public void removeAnimals(Animal animal, int howMany) {
        if (farmerLiveStock.get(animal) >= howMany) {
            farmerLiveStock.computeIfPresent(animal, (k, v) -> v - howMany);
        } else if (farmerLiveStock.get(animal) == 0) {
            farmerLiveStock.remove(animal);
        } else {
            System.out.println("No animal was removed from " + name + "'s stock.");
        }
    }

    public Animal rollDice(Die dice) {
        int randomNo = random.nextInt(12);
        return dice.getDiceSides().get(randomNo);
    }

}
