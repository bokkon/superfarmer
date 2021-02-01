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

    public Game() {
        this.animalStock = Initializer.createAnimalStock();
        this.dice1 = Initializer.createDice(new Sheep(), new Cow(), new Wolf());
        this.dice2 = Initializer.createDice(new Pig(), new Horse(), new Fox());
    }

    public List<Farmer> getFarmers() {
        return farmers;
    }

    public AnimalStock getAnimalStock() {
        return animalStock;
    }

    public void init() {
        this.farmers.addAll(Arrays.asList(Initializer.createPlayer("Jen"), Initializer.createPlayer("Bob")));
    }

    //TODO
    public void run() {
        while(animalStock.getAnimalCount() != 0 || !thereIsAWinner) {
            doRound();
        }
    }

    //TODO
    private void doRound() {
        for (Farmer actFarmer: farmers) {
            Map<Animal, Map<Animal, Integer>> possChanges = getPossibleChanges(actFarmer);
            actFarmer.change(possChanges);
            if (checkWin(actFarmer)){
                thereIsAWinner = true;
                System.out.println("Congratulations! " + actFarmer.getName() + " you win!");
                System.exit(0);
            }
            Animal result1 = actFarmer.rollDice(dice1);
            Animal result2 = actFarmer.rollDice(dice2);
            evaluateDiceResult(result1, result2);
            if (checkWin(actFarmer)){
                thereIsAWinner = true;
                System.out.println("Congratulations! " + actFarmer.getName() + " you win!");
                System.exit(0);
            }
        }
    }

    //TODO check availability + TODO change back to private
    public Map<Animal, Map<Animal, Integer>> getPossibleChanges(Farmer actFarmer) {
        Map<Animal, Map<Animal, Integer>> changes1 = getPossibleSimpleChanges(actFarmer);
        return changes1;
    }

    private Map<Animal, Map<Animal, Integer>> getPossibleSimpleChanges(Farmer actFarmer) {
        Map<Animal, Map<Animal, Integer>> result = new HashMap<>();
        Map<Animal, Integer> actStock = actFarmer.getFarmerLiveStock();
        for (Animal key: actStock.keySet()) {
            result.putIfAbsent(key, key.changeableTo());
        }
        return result;
    }

    //TODO
    public Map<Animal, Map<Animal, Double>> getPossibleMultipleChanges(Farmer actFarmer) {
        return new HashMap<>();
    }

    //TODO
    private void evaluateDiceResult(Animal result1, Animal result2) {
    }

    private boolean checkWin(Farmer actFarmer) {
        Map<Animal, Integer> farmerAnimals = actFarmer.getFarmerLiveStock();
        int animalCounter = (int) farmerAnimals.keySet().stream()
                .filter(key -> !key.equals(new SmallDog()) && !key.equals(new BigDog())).count();
        return animalCounter == 5;
    }

    private void printDiceResult(Animal result) {
        System.out.println("The result of the dice rolled is:  " + result.getClass().getSimpleName());
    }
}
