package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.List;

public class AnimalStock implements CollectAnimal {

    private List<Animal> animals;

    public AnimalStock(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    @Override
    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }

    @Override
    public void addAnimals(List<Animal> animals) {
        this.animals.addAll(animals);
    }
}
