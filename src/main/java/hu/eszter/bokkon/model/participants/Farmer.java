package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Farmer is the player, who can collect animals. 2-4 players can play in 1 game.
 */
public class Farmer implements StockProvider {

    private final String name;
    private Map<Animal, Integer> animalStock;
    private Map<Animal, Map<Animal, Double>> actualPossibleChanges = new LinkedHashMap<>();

    public Farmer(String name) {
        this.name = name;
        this.animalStock = new LinkedHashMap<>();
        animalStock.put(new Rabbit(), 0);
        animalStock.put(new Sheep(), 0);
        animalStock.put(new Pig(), 0);
        animalStock.put(new Cow(), 0);
        animalStock.put(new Horse(), 0);
        animalStock.put(new SmallDog(), 0);
        animalStock.put(new BigDog(), 0);
    }

    public String getName() {
        return name;
    }

    @Override
    public Map<Animal, Integer> getAnimalStock() {
        return animalStock;
    }

    /**
     * Implementation of addAnimals method of StockProvider interface. Adds given number of the same type of animal to
     * the animal stock of the farmer(player), which is an empty map by default. If the the stock doesn't contain the
     * type of animal yet, it adds it to the stock.
     *
     * @param animal  type of animal to be added
     * @param howMany number of the same type of animal to be added
     */
    @Override
    public void addAnimals(Animal animal, int howMany) {
        if (howMany >= 1) {
            animalStock.computeIfPresent(animal, (k, v) -> v + howMany);
        } else {
            System.out.println("No animal was added to " + name + "'s stock.");
        }
    }

    /**
     * Implementation of removeAnimals method of StockProvider interface. Removes given number of the same type of animal
     * from the animal stock of the farmer(player) if there are available number of animals in the stock, which is an
     * empty map by default. In case the number of animals becomes 0, it removes the tape of animal from the stock.
     *
     * @param animal  type of animal to be removed
     * @param howMany number of the same type of animal to be removed
     */
    @Override
    public void removeAnimals(Animal animal, int howMany) {
        if (animalStock.get(animal) >= howMany) {
            animalStock.computeIfPresent(animal, (k, v) -> v - howMany);
        }  else {
            System.out.println("No animal was removed from " + name + "'s stock.");
        }
    }

    public void setOneAnimalCountToZero(Animal animalToSetZero) {
        animalStock.put(animalToSetZero, 0);
    }

    public void setAnimalsCountToZero() {
        setOneAnimalCountToZero(new Rabbit());
        setOneAnimalCountToZero(new Sheep());
        setOneAnimalCountToZero(new Pig());
        setOneAnimalCountToZero(new Cow());
        setOneAnimalCountToZero(new BigDog());

    }

    public Animal rollDice(Die dice) {
        return dice.getRandomSide();
    }

    public Map<Animal, Map<Animal, Double>> getActualPossibleChanges() {
        return actualPossibleChanges;
    }

    public void setActualPossibleChanges(Animal animal, Map<Animal, Double> revisedExchangeTable) {
        this.actualPossibleChanges.put(animal, revisedExchangeTable);
    }

    public void clearActualPossibleChanges() {
        this.actualPossibleChanges.clear();
    }
}
