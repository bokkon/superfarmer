package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.AnimalBaseStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.Farmer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Initializer {

    public static AnimalBaseStock createAnimalStock() {
        return new AnimalBaseStock();
    }

    public static Fox createFox() {
        return new Fox();
    }

    private static Wolf createWolf() {
        return new Wolf();
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
