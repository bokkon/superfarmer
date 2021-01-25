package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.List;

public interface CollectAnimal {

    void addAnimal(Animal newAnimal);

    void addAnimals(List<Animal> newAnimals);
}
