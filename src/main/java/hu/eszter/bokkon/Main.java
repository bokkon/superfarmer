package hu.eszter.bokkon;

import hu.eszter.bokkon.model.participants.AnimalStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.service.Initializer;

public class Main {

    public static void main (String[] args) {

        Initializer initializer = new Initializer();
        AnimalStock animals = initializer.createAnimalStock();
        Dice dice1 = initializer.createDice("sheep", "cow", "wolf");
        Dice dice2 = initializer.createDice("pig", "horse", "fox");
        Farmer farmer1 = new Farmer("Jen");
        Farmer farmer2 = new Farmer("Bob");
        Game newGame = new Game(animals, dice1, dice2, farmer1, farmer2);
    }
}
