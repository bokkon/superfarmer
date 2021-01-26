package hu.eszter.bokkon;

import hu.eszter.bokkon.model.participants.AnimalStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.service.Initializer;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main (String[] args) {

        Initializer initializer = new Initializer();
        AnimalStock allAnimals = initializer.createAnimalStock();
        Dice dice1 = initializer.createDice("Sheep", "Cow", "Wolf");
        Dice dice2 = initializer.createDice("Pig", "Horse", "Fox");
        List<Farmer> farmers = Arrays.asList(new Farmer("Jen"), new Farmer("Bob"));
        Game newGame = new Game(allAnimals, dice1, dice2, farmers);
    }
}
