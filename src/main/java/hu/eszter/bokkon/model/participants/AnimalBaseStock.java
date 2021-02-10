package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Base Stock providing all farm animals for 1 game.
 */
public class AnimalBaseStock implements MoveAnimal {

    private Map<Animal, Integer> liveStock = new LinkedHashMap<>();

    public AnimalBaseStock() {
        liveStock.put(new Rabbit(), 60);
        liveStock.put(new Sheep(), 24);
        liveStock.put(new Pig(), 20);
        liveStock.put(new Cow(), 12);
        liveStock.put(new Horse(), 6);
        liveStock.put(new SmallDog(), 4);
        liveStock.put(new BigDog(), 2);
    }

    public Map<Animal, Integer> getLiveStock() {
        return liveStock;
    }

    /**
     * Implementation of addAnimals method of MoveAnimal interface. Adds given number of the same type of animal to
     * the base animal stock, which is a map containing animal types as key and the number of that type of
     * animal as value, which is 0 by default.
     *
     * @param animal  type of animal to be added
     * @param howMany number of the same type of animal to be added
     */
    @Override
    public void addAnimals(Animal animal, int howMany) {
        if (howMany >= 1) {
            liveStock.computeIfPresent(animal, (k, v) -> v + howMany);
        } else {
            System.out.println("No animal was added to the base stock.");
        }
    }

    /**
     * Implementation of removeAnimals method of MoveAnimal interface. Removes given number of the same type of animal
     * from the base animal stock if there are available number of animals. In case there are no available animals from
     * 1 type, the animal type as key remains and its value becomes 0.
     *
     * @param animal  type of animal to be removed
     * @param howMany number of the same type of animal to be removed
     */
    @Override
    public void removeAnimals(Animal animal, int howMany) {
        if (liveStock.get(animal) >= howMany) {
            liveStock.computeIfPresent(animal, (k, v) -> v - howMany);
        } else {
            System.out.println("No animal was removed from the base stock.");
        }

    }
}
