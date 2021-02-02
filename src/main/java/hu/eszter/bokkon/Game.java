package hu.eszter.bokkon;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.AnimalBaseStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.service.Initializer;

import java.util.*;

public class Game {

    private AnimalBaseStock animalStock;
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

    public AnimalBaseStock getAnimalStock() {
        return animalStock;
    }

    public void init() {
        this.farmers.addAll(Arrays.asList(Initializer.createPlayer("Jen"), Initializer.createPlayer("Bob")));
    }

    //TODO
    public void run() {
        while (animalStock.getAnimalCount() != 0 || !thereIsAWinner) {
            doRound();
        }
    }

    //TODO
    private void doRound() {
        for (Farmer actFarmer : farmers) {
            Map<Animal, Map<Animal, Double>> possChanges = getPossibleChanges(actFarmer, animalStock.getLiveStock());
            actFarmer.change(possChanges);
            if (checkWin(actFarmer)) {
                thereIsAWinner = true;
                System.out.println("Congratulations! " + actFarmer.getName() + " you win!");
                System.exit(0);
            }
            Animal result1 = actFarmer.rollDice(dice1);
            Animal result2 = actFarmer.rollDice(dice2);
            evaluateDiceResult(result1, result2);
            if (checkWin(actFarmer)) {
                thereIsAWinner = true;
                System.out.println("Congratulations! " + actFarmer.getName() + " you win!");
                System.exit(0);
            }
        }
    }

    /**
     * Calculates possible changes for 1 farmer(player) in 1 round, stores the result in a map.
     *
     * @param actFarmer actual farmer(player) whose round it is
     * @return Map containing all possible changes of the actual player
     */
    //TODO change back to private
    Map<Animal, Map<Animal, Double>> getPossibleChanges(Farmer actFarmer, Map<Animal, Integer> stockToCheck) {
        Map<Animal, Map<Animal, Double>> result = new LinkedHashMap<>();
        Map<Animal, Integer> actStock = actFarmer.getFarmerLiveStock();
        for (Animal actAnimal : actStock.keySet()) {
            Map<Animal, Double> actExchangeTable = actAnimal.changeableTo();
            Map<Animal, Double> revisedExchangeTable = new HashMap<>();
            for (Animal excAnimal : actExchangeTable.keySet()) {
                double exchangeRate = actExchangeTable.get(excAnimal);
                boolean available = checkAvailability(excAnimal, exchangeRate, stockToCheck);
                if (available && (exchangeRate < 1.0 && actStock.get(actAnimal) >= 1 / exchangeRate || exchangeRate >= 1.0)) {
                    revisedExchangeTable.put(excAnimal, exchangeRate);
                }
            }
            result.put(actAnimal, revisedExchangeTable);
        }
        return result;
    }

    /**
     * Checks availability of the given number of given animal in the given stock.
     *
     * @param animal type of animal whose availabilty is to be checked
     * @param exchangeRate exchangeRate of animal referring to the number of animals to be checked
     *                     (if the value of the exchangeRate is lower than 1, it means that we need 1 of that animal)
     * @param stockToCheck animal stock to be checked
     * @return boolean value whether the animal is available in the stock
     */
    private boolean checkAvailability(Animal animal, double exchangeRate, Map<Animal, Integer> stockToCheck) {
        if (exchangeRate < 1.0) {
            return stockToCheck.get(animal) >= 1;
        } else {
            return stockToCheck.get(animal) >= exchangeRate;
        }
    }

    //TODO
    private void evaluateDiceResult(Animal result1, Animal result2) {
    }

    /**
     * Checks if the actual farmer(player) whose round it is, whether or no has 5 different type of animals
     * which are not dogs.
     *
     * @param actFarmer is the farmer(player) whose round it is
     * @return integer value how many animal types the farmer has except for dogs
     */
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
