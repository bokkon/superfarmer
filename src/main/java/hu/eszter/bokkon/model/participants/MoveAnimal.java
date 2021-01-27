package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.List;

public interface MoveAnimal {

    void addAnimal(String newAnimal);

    void addAnimals(List<String> newAnimals);

    void removeAnimal(String newAnimal);

    void removeAnimals(List<String> newAnimals);
}
