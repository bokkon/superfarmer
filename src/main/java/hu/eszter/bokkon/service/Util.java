package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.Collections;
import java.util.Map;

public class Util {

    /**
     * Displays all the possible exchanges between 2 stocks: the actual farmer's animal stock and another animal stock.
     *
     * @param possibleChanges is a map data structure storing the possible exchanges between 2 animal stocks
     */
    public void printPossibleChangesMap(Map<Animal, Map<Animal, Double>> possibleChanges) {
        int line = 1;
        for (Map.Entry<Animal, Map<Animal, Double>> entry : possibleChanges.entrySet()) {
            String animalName = entry.getKey().getClass().getSimpleName();
            Map<Animal, Double> actMap = entry.getValue();
            for (Animal actAnimal : actMap.keySet()) {
                System.out.print((line > 9 ? "" : " ") + line++ + ".) ");
                double count = actMap.get(actAnimal);
                if (count >= 1.0) {
                    String spaces = getSpaces(8, animalName);
                    System.out.println("1 " + animalName + spaces + " for:   "
                            + (int) count + " " + actAnimal.getClass().getSimpleName() + (count == 1 ? "" : "s"));
                } else if (count > 0.0) {
                    String spaces = getSpaces(7, animalName);
                    System.out.println((int)(1 / count) + " " + animalName + ((int)(1 / count) == 1 ? " " : "s") + spaces
                            + " for:   1 " +  actAnimal.getClass().getSimpleName());
                }
            }
        }
    }

    private String getSpaces (int maxLength, String animalName) {
        return String.join("", Collections.nCopies(maxLength - animalName.length(), " "));
    }
}
