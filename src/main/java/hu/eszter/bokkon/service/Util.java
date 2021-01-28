package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.Collections;
import java.util.Map;

public class Util {

    public void printSimpleChangesMap(Map<Animal, Map<Animal, Integer>> map) {
        for (Map.Entry<Animal, Map<Animal, Integer>> entry : map.entrySet()) {
            String animalName = entry.getKey().getClass().getSimpleName();
            Map<Animal, Integer> actMap = entry.getValue();
            for (Animal actAnimal : actMap.keySet()) {
                int count = actMap.get(actAnimal);
                System.out.println(animalName + String.join("", Collections.nCopies(8-animalName.length(), " "))
                        + " for:   " + count + " " + actAnimal.getClass().getSimpleName() + (count == 1 ? "" : "s"));
            }
        }
    }

}
