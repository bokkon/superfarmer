package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalStock implements MoveAnimal {

    private Map<Animal, Integer> liveStock = new HashMap<>();
    private int animalCount;

    public AnimalStock() {
        animalCount = 128;
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

    public int getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(int animalCount) {
        this.animalCount = animalCount;
    }

    @Override
    public void addAnimal(Animal animal) {
        liveStock.put(animal, liveStock.get(animal) + 1);
    }

    @Override
    public void addAnimals(List<Animal> animals) {
        Animal animal = animals.get(0);
        liveStock.put(animal, liveStock.get(animal) + animals.size());
    }

    @Override
    public void removeAnimal(Animal animal) {
        if (liveStock.get(animal) > 0) {
            liveStock.put(animal, liveStock.get(animal) - 1);
        }
    }

    @Override
    public void removeAnimals(List<Animal> animals) {
        Animal animal = animals.get(0);
        if (liveStock.get(animal) >= animals.size()){
            liveStock.put(animal, liveStock.get(animal) - animals.size());
        }
    }
}
