package hu.eszter.bokkon;

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
        while(!animalStock.getLiveStock().isEmpty() || !thereIsAWinner) {
            doRound();
        }
    }

    //TODO
    private void doRound() {
        for (Farmer actFarmer: farmers) {
            Map<String, String> actPossibleChanges = getPossibleChanges(actFarmer);
            actFarmer.change(actPossibleChanges);
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

    private Map<String, String> getPossibleChanges(Farmer act) {
        Map<String, String> possibleChanges = new HashMap<>();
        Map<String, Integer> farmerLiveStock = act.getFarmerLiveStock();
        if (farmerLiveStock.isEmpty()) {
            return possibleChanges;
        }
        Map<String, String> exchangeRules = this.exchangeTable.getExchanges();
        for (String key : exchangeRules.keySet()) {
            String value = exchangeRules.get(key);
            if (farmerLiveStock.containsKey(key)) {
                possibleChanges.put(key, value);
            }
            if (key.charAt(1) == ' ') {
                String[] temp = key.split(" ");
                if (farmerLiveStock.containsKey(temp[1]) && farmerLiveStock.get(temp[1]) >= Integer.parseInt(temp[0])) {
                    possibleChanges.put(key, value);
                }
            }
        }
        return possibleChanges;
    }

    //TODO
    private Map<String, String> changeIsAvailable(Map<String, String> mapToCheck) {
        Map<String, String> result = new HashMap<>();
        for (String key: mapToCheck.keySet()) {
            String val = mapToCheck.get(key);
            String[] temp = val.split(",");
            if (temp.length == 1 && animalStock.getLiveStock().get(val) > 0) {
                result.put(key, val);
            }
        }
        return result;
    }

    //TODO
    private void evaluateDiceResult(String result1, String result2) {
    }

    private boolean checkWin(Farmer actFarmer) {
        Map<String, Integer> farmerAnimals = actFarmer.getFarmerLiveStock();
        int animalCounter = (int) farmerAnimals.keySet().stream().filter(key -> !key.equals("SmallDog") && !key.equals("BigDog")).count();
        return animalCounter == 5;
    }

    private void printDiceResult(String result) {
        System.out.println("The result of the dice rolled is:  " + result);
    }
}
