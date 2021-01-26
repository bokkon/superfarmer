package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.List;

public class AnimalStock implements MoveAnimal {

    private List<Animal> liveStock;

    public AnimalStock(List<Animal> animals) {
        this.liveStock = animals;
    }

    public List<Animal> getLiveStock() {
        return liveStock;
    }

    @Override
    public void addAnimal(Animal animal) {
        this.liveStock.add(animal);
    }

    @Override
    public void addAnimals(List<Animal> animals) {
        this.liveStock.addAll(animals);
    }

    @Override
    public void removeAnimal(Animal animal) {
        liveStock.remove(animal);
    }

    @Override
    public void removeAnimals(List<Animal> animals) {
        liveStock.removeAll(animals);
    }
}
