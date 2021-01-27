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
        liveStock.put("Rabbit", 60);
        liveStock.put("Sheep", 24);
        liveStock.put("Pig", 20);
        liveStock.put("Cow", 12);
        liveStock.put("Horse", 6);
        liveStock.put("SmallDog", 4);
        liveStock.put("BigDog", 2);
    }

    public Map<String, Integer> getLiveStock() {
        return liveStock;
    }

    public Integer getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(Integer animalCount) {
        this.animalCount = animalCount;
    }

    @Override
    public void addAnimal(String animal) {
        liveStock.put(animal, liveStock.get(animal) + 1);
    }

    @Override
    public void addAnimals(List<String> animals) {
        for (String actAnimal: animals) {
            addAnimal(actAnimal);
        }
    }

    @Override
    public void removeAnimal(String animal) {
        if (liveStock.get(animal) > 0) {
            liveStock.put(animal, liveStock.get(animal) - 1);
        }
    }

    @Override
    public void removeAnimals(List<String> animals) {
        for (String actAnimal: animals) {
            removeAnimal(actAnimal);
        }
    }
}
