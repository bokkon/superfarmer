package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalStock implements MoveAnimal {

    private Integer animalCount;
    private Map<String, Integer> liveStock = new HashMap<>();

    public AnimalStock() {
        animalCount = 128;
    }

    public Map<String, Integer> getLiveStock() {
        return liveStock;
    }

    @Override
    public void addAnimal(Animal animal) {
        String name = animal.getClass().getSimpleName();
        liveStock.put(name, liveStock.get(name) + 1);
    }

    @Override
    public void addAnimals(List<Animal> animals) {
        String name = animals.get(0).getClass().getSimpleName();
        liveStock.put(name, liveStock.get(name) + animals.size());
    }

    @Override
    public void removeAnimal(Animal animal) {
        String name = animal.getClass().getSimpleName();
        if (liveStock.get(name) >= 0) {
            liveStock.replace(name, liveStock.get(name) - 1);
        }
    }

    @Override
    public void removeAnimals(List<Animal> animals) {
        String name = animals.get(0).getClass().getSimpleName();
        if (liveStock.get(name) >= animals.size()) {
            liveStock.replace(name, liveStock.get(name) - animals.size());
        }
    }
}
