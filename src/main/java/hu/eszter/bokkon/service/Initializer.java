package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.AnimalStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.ExchangeTable;
import hu.eszter.bokkon.model.participants.Farmer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Initializer {

    public AnimalStock createAnimalStock() {
        List<Animal> animals = createFarmAnimals();
        return new AnimalStock(animals);
    }

    private List<Animal> createFarmAnimals() {
        List<Animal> animals = new LinkedList<>();
        animals.addAll(Stream.generate(Rabbit::new).limit(60).collect(Collectors.toList()));
        animals.addAll(Stream.generate(Sheep::new).limit(24).collect(Collectors.toList()));
        animals.addAll(Stream.generate(Pig::new).limit(20).collect(Collectors.toList()));
        animals.addAll(Stream.generate(Cow::new).limit(12).collect(Collectors.toList()));
        animals.addAll(Stream.generate(Horse::new).limit(6).collect(Collectors.toList()));
        animals.addAll(Stream.generate(SmallDog::new).limit(4).collect(Collectors.toList()));
        animals.addAll(Stream.generate(BigDog::new).limit(2).collect(Collectors.toList()));
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
