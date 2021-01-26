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
            provideChangePossibilities(actFarmer);
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
            evaluateDiceResult(result1, result2);
            if (checkWin(actFarmer)){
                thereIsAWinner = true;
                System.out.println("Congratulations! " + actFarmer.getName() + " you win!");
                System.exit(0);
            }
        }
    }

    //TODO
    private void provideChangePossibilities(Farmer act) {
        List<Animal> changeable = new ArrayList<>();
    }

    //TODO
    private void evaluateDiceResult(String result1, String result2) {
    }

    private boolean checkWin(Farmer actFarmer) {
        Map<String, Integer> farmerAnimals = actFarmer.getFarmerLiveStock();
        return farmerAnimals.size() >= 5;
    }

    private void printDiceResult(String result) {
        System.out.println("The result of the dice rolled is:  " + result);
    }
}
