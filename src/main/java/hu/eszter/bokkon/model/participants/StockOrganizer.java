package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.Map;

public interface StockOrganizer {

    /**
     * Adds given number of the same type of animal to an animal stock
     * @param animal type of animal to be added
     * @param howMany number of the same type of animal to be added
     */
    void addAnimals(Animal animal, int howMany);

    /**
     * Removes given number of the same type of animal from an animal stock
     * @param animal type of animal to be removed
     * @param howMany number of the same type of animal to be removed
     */
    void removeAnimals(Animal animal, int howMany);

    Map<Animal, Integer> getAnimalStock();
}
