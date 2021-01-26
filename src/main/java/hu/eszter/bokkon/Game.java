package hu.eszter.bokkon;

import hu.eszter.bokkon.model.animal.Animal;
import hu.eszter.bokkon.model.participants.AnimalStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.ExchangeTable;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.service.Initializer;

import java.util.*;

public class Game {

    private AnimalStock animalStock;
    private final Dice dice1;
    private final Dice dice2;
    private final ExchangeTable exchangeTable;
    private List<Farmer> farmers = new ArrayList<>();
    private boolean thereIsAWinner = false;
    Initializer initializer = new Initializer();

    public Game() {
        this.dice1 = initializer.createDice("Sheep", "Cow", "Wolf");
        this.dice2 = initializer.createDice("Pig", "Horse", "Fox");
        this.exchangeTable = initializer.createExchangeTable();
        this.animalStock = initializer.createAnimalStock();
    }

    public void init() {
        this.farmers = Arrays.asList(initializer.createPlayer("Jen"), initializer.createPlayer("Bob"));
    }

    public void run() {
        while(!animalStock.getLiveStock().isEmpty() || !thereIsAWinner) {
            doRound();
        }
    }

    private void doRound() {
        for (Farmer actFarmer: farmers) {
            actFarmer.checkChangePossibilities();
            actFarmer.change();
            if (checkWin(actFarmer)){
                thereIsAWinner = true;
                System.out.println("Congratulations! " + actFarmer.getName() + " you win!");
                System.exit(0);
            }
            String result1 = actFarmer.rollDice(dice1);
            printDiceResult(result1);
            String result2 = actFarmer.rollDice(dice2);
            printDiceResult(result2);
            actFarmer.evaluateDiceResult(result1, result2);
        }
    }

    private boolean checkWin(Farmer actFarmer) {
        List<Animal> farmerAnimals = actFarmer.getFarmerLiveStock();
        Set<String> animals = new HashSet<>();
        for (Animal actAnimal: farmerAnimals) {
            animals.add(actAnimal.getClass().getSimpleName());
        }
        return animals.contains("Rabbit")
                && animals.contains("Sheep") && animals.contains("Pig")
                && animals.contains("Cow") && animals.contains("Horse");
    }

    private void printDiceResult(String result) {
        System.out.println("The result of the dice rolled is:  " + result);
    }
}
