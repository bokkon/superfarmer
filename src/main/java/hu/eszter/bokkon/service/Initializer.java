package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.AnimalBaseStock;
import hu.eszter.bokkon.model.participants.Die;
import hu.eszter.bokkon.model.participants.Farmer;

import java.util.*;

public class Initializer {

    public static AnimalBaseStock createAnimalStock() {
        return new AnimalBaseStock();
    }

    public static Die createDice(Animal a1, Animal a2, Animal a3) {
        List<Animal> diceSides = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            diceSides.add(Animal.RABBIT);
            if (i < 2) {
                diceSides.add(Animal.SHEEP);
            }
        }
        diceSides.add(Animal.PIG);
        diceSides.addAll(Arrays.asList(a1, a2, a3));
        return new Die(diceSides);
    }

    public static Farmer createPlayer(String name) {
        return new Farmer(name);
    }

    public static ExchangeService setUpExchangeService() {
        return new ExchangeService();
    }

    public static DiceRollService setUpDiceRollService() {
        return new DiceRollService();
    }
}
