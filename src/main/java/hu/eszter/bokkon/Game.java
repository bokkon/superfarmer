package hu.eszter.bokkon;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.AnimalStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.service.Initializer;

import java.util.*;

public class Game {

    private AnimalStock animalStock;
    private final Dice dice1;
    private final Dice dice2;
    private List<Farmer> farmers = new ArrayList<>();
    private boolean thereIsAWinner = false;
    Initializer initializer = new Initializer();

    public Game() {
        this.animalStock = initializer.createAnimalStock();
        this.dice1 = initializer.createDice(new Sheep(), new Cow(), new Wolf());
        this.dice2 = initializer.createDice(new Pig(), new Horse(), new Fox());
    }

    public List<Farmer> getFarmers() {
        return farmers;
    }

    public AnimalStock getAnimalStock() {
        return animalStock;
    }

    public void init() {
        this.farmers.addAll(Arrays.asList(initializer.createPlayer("Jen"), initializer.createPlayer("Bob")));
    }

    public void run() {
        while(animalStock.getAnimalCount() != 0 || !thereIsAWinner) {
            doRound();
        }
    }

    //TODO
    private void doRound() {
        for (Farmer actFarmer: farmers) {
            getPossibleChanges(actFarmer);
            actFarmer.change();
            if (checkWin(actFarmer)){
                thereIsAWinner = true;
                System.out.println("Congratulations! " + actFarmer.getName() + " you win!");
                System.exit(0);
            }
            Animal result1 = actFarmer.rollDice(dice1);
            printDiceResult(result1);
            Animal result2 = actFarmer.rollDice(dice2);
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
    private void getPossibleChanges(Farmer act) {
    }

    //TODO
    private void evaluateDiceResult(Animal result1, Animal result2) {
    }

    private boolean checkWin(Farmer actFarmer) {
        Map<Animal, Integer> farmerAnimals = actFarmer.getFarmerLiveStock();
        int animalCounter = (int) farmerAnimals.keySet().stream().filter(key -> !key.equals(new SmallDog()) && !key.equals(new BigDog())).count();
        return animalCounter == 5;
    }

    private void printDiceResult(Animal result) {
        System.out.println("The result of the dice rolled is:  " + result.getClass().getSimpleName());
    }
}
