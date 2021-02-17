package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.AnimalBaseStock;
import hu.eszter.bokkon.model.participants.Die;
import hu.eszter.bokkon.model.participants.Farmer;

import java.util.*;

public class Initializer {

    public static void setUpExchangeRateTable() {
        Util.initializeExchangeRatesBase();
        Util.registerExchangeRate(Animal.SHEEP, Animal.RABBIT, 6);
        Util.registerExchangeRate(Animal.PIG, Animal.SHEEP, 2);
        Util.registerExchangeRate(Animal.COW, Animal.PIG, 3);
        Util.registerExchangeRate(Animal.HORSE, Animal.COW, 2);
        Util.registerExchangeRate(Animal.SMALLDOG, Animal.SHEEP, 1);
        Util.registerExchangeRate(Animal.BIGDOG, Animal.COW, 1);
    }

    public static AnimalBaseStock createAnimalStock() {
        return new AnimalBaseStock();
    }

    public static Farmer createPlayer(String name) {
        return new Farmer(name);
    }

    public static ExchangeService setUpExchangeService() {
        return new ExchangeService();
    }

    public static DiceRollService setUpDiceRollService() {
        Die die1 = createDice(Animal.SHEEP, Animal.COW, Animal.WOLF);
        Die die2 = createDice(Animal.PIG, Animal.HORSE, Animal.FOX);
        return new DiceRollService(die1, die2);
    }

    private static Die createDice(Animal a1, Animal a2, Animal a3) {
        List<Animal> diceSides = new ArrayList<>(Collections.nCopies(6, Animal.RABBIT));
        diceSides.addAll(Collections.nCopies(2, Animal.SHEEP));
        diceSides.add(Animal.PIG);
        diceSides.addAll(Arrays.asList(a1, a2, a3));
        return new Die(diceSides);
    }
}
