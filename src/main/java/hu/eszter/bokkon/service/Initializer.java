package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.participants.AnimalStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.ExchangeTable;
import hu.eszter.bokkon.model.participants.Farmer;

import java.util.*;

public class Initializer {

    public AnimalStock createAnimalStock() {
        Map<String, Integer> animals = createFarmAnimals();
        return new AnimalStock();
    }

    private Map<String, Integer> createFarmAnimals() {
        Map<String, Integer> animals = new HashMap<>();
        animals.put("Rabbit", 60);
        animals.put("Sheep", 24);
        animals.put("Pig", 20);
        animals.put("Cow", 12);
        animals.put("Horse", 6);
        animals.put("SmallDog", 4);
        animals.put("BigDog", 2);
        return animals;
    }

    public Dice createDice(String a1, String a2, String a3) {
        List<String> diceSides = new ArrayList<>(Collections.nCopies(6, "Rabbit"));
        List<String> actList = Collections.nCopies(2, "Sheep");
        diceSides.addAll(actList);
        diceSides.add("Pig");
        diceSides.addAll(Arrays.asList(a1, a2, a3));
        return new Dice(diceSides);
    }

    public ExchangeTable createExchangeTable(){
        return new ExchangeTable();
    }

    public Farmer createPlayer(String name) {
        return new Farmer(name);
    }
}
