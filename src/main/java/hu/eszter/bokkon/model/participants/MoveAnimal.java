package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.List;

public interface MoveAnimal {

    void addAnimal(Animal newAnimal);

    void addAnimals(List<Animal> newAnimals);

    void removeAnimal(Animal newAnimal);

    void removeAnimals(List<Animal> newAnimals);
}
