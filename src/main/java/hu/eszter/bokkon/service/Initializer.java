package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.Animal;
import hu.eszter.bokkon.model.animal.Pig;
import hu.eszter.bokkon.model.animal.Rabbit;
import hu.eszter.bokkon.model.animal.Sheep;
import hu.eszter.bokkon.model.participants.AnimalStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.Farmer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Initializer {

    public static AnimalStock createAnimalStock() {
        return new AnimalStock();
    }

    public static Dice createDice(Animal a1, Animal a2, Animal a3) {
        List<Animal> diceSides = new ArrayList<>();
        diceSides.addAll(Stream.generate(Rabbit::new).limit(6).collect(Collectors.toList()));
        diceSides.addAll(Stream.generate(Sheep::new).limit(2).collect(Collectors.toList()));
        diceSides.add(new Pig());
        diceSides.addAll(Arrays.asList(a1, a2, a3));
        return new Dice(diceSides);
    }

    public static Farmer createPlayer(String name) {
        return new Farmer(name);
    }
}
