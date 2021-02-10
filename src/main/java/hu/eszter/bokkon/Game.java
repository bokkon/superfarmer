package hu.eszter.bokkon;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.AnimalBaseStock;
import hu.eszter.bokkon.model.participants.Die;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.service.Initializer;
import hu.eszter.bokkon.service.Util;

import java.util.*;

public class Game {

    private AnimalBaseStock animalBaseStock;
    private final Die dice1;
    private final Die dice2;
    private List<Farmer> farmers = new ArrayList<>();
    private boolean thereIsAWinner = false;
    private final Scanner scan;

    public Game() {
        this.animalBaseStock = Initializer.createAnimalStock();
        this.dice1 = Initializer.createDice(new Sheep(), new Cow(), new Wolf());
        this.dice2 = Initializer.createDice(new Pig(), new Horse(), new Fox());
        scan = new Scanner(System.in);
    }

    public List<Farmer> getFarmers() {
        return farmers;
    }

    public AnimalBaseStock getAnimalBaseStock() {
        return animalBaseStock;
    }

    //TODO get how many players and their names from user input 2-6 players
    public void init() {
        this.farmers.addAll(Arrays.asList(Initializer.createPlayer("Jen"), Initializer.createPlayer("Bob")));
    }

    public void run() {
        Util.startMessage();
        Util.displayAllStocks(animalBaseStock.getLiveStock(), farmers);
        while (!thereIsAWinner) {
            doRound();
            if (animalBaseStock.getLiveStock().values().stream().mapToInt(v -> v).sum() == 0) {
                System.out.println("Main stock is empty!");
                return;
            }
        }
    }

    private void doRound() {
        for (Farmer actFarmer : farmers) {
            displayActualFarmersName(actFarmer);
            System.out.println(transactExchange(actFarmer));
            Util.displayAllStocks(animalBaseStock.getLiveStock(), farmers);
            if (checkWin(actFarmer)) {
                thereIsAWinner = true;
                System.out.println("Congratulations! ".toUpperCase() + Util.getAnsiBrightRed() + actFarmer.getName() + Util.getReturnColour()
                        + " you are the " + Util.getAnsiBrightGreen() + "Superfarmer!" + Util.getReturnColour());
                scan.close();
                return;
            }
            transactDiceRoll(actFarmer);
            Util.displayAllStocks(animalBaseStock.getLiveStock(), farmers);
            if (checkWin(actFarmer)) {
                thereIsAWinner = true;
                System.out.println("Congratulations! " + actFarmer.getName() + " are the Superfarmer!");
                scan.close();
                return;
            }
            askToContinue();
        }
    }

    private void displayActualFarmersName(Farmer actFarmer) {
        System.out.println("Now " + actFarmer.getName() + " is playing.");
        System.out.println();
    }


    //TODO refactor so that exchange can be possible between any farmers(players) too
    private String transactExchange(Farmer actFarmer) {
        Map<Animal, Map<Animal, Double>> possExchanges = getPossibleChanges(actFarmer, animalBaseStock.getLiveStock());
        if (possExchanges.size() == 0) {
            return "There are no possible exchanges for " + actFarmer.getName() + " !";
        }
        System.out.println("Possible changes for " + actFarmer.getName() + ":\n");
        Util.printPossibleChangesMap(possExchanges);
        int countPossibilities = possExchanges.values().stream().mapToInt(Map::size).sum();
        int selectedExchange = getExchangeSelectionInput(actFarmer, countPossibilities);
        return executeExchange(actFarmer, possExchanges, selectedExchange) ?
                "Exchange was successful!" : actFarmer.getName() + " decided not to exchange!";
    }


    private int getExchangeSelectionInput(Farmer actFarmer, int maxValue) {
        System.out.println(actFarmer.getName() + " please pick an exchange! ( 1 " + (maxValue == 1 ? "" : "to " + maxValue) + " ) Pick 0 if you don't wish to exchange.");
        String input;
        do {
            input = scan.next();
            if ("q".equals(input.toLowerCase())) {
                System.exit(0);
            }
        } while (!checkInputNumber(input, maxValue));
        return Integer.parseInt(input);
    }

    private boolean executeExchange(Farmer actFarmer, Map<Animal, Map<Animal, Double>> possibleExchanges, int numberOfSelected) {
        if (numberOfSelected == 0) {
            return false;
        }
        int line = 1;
        for (Animal exchangeAnimal : possibleExchanges.keySet()) {
            Map<Animal, Double> actMap = possibleExchanges.get(exchangeAnimal);
            for (Animal returnAnimal : actMap.keySet()) {
                if (line++ == numberOfSelected) {
                    double count = actMap.get(returnAnimal);
                    if (count >= 1.0) {
                        actFarmer.removeAnimals(exchangeAnimal, 1);
                        actFarmer.addAnimals(returnAnimal, (int) count);
                        animalBaseStock.addAnimals(exchangeAnimal, 1);
                        animalBaseStock.removeAnimals(returnAnimal, (int) count);
                        return true;
                    } else {
                        actFarmer.removeAnimals(exchangeAnimal, (int) (1 / count));
                        actFarmer.addAnimals(returnAnimal, 1);
                        animalBaseStock.addAnimals(exchangeAnimal, (int) (1 / count));
                        animalBaseStock.removeAnimals(returnAnimal, 1);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Checks the input required from the player.
     *
     * @param input    an input received from the player in String format
     * @param maxValue is the maximum value the input can have after converted into Integer
     * @return whether the input is valid
     */
    private boolean checkInputNumber(String input, int maxValue) {
        if (input == null) {
            return false;
        }
        try {
            int number = Integer.parseInt(input);
            if (number > maxValue || number < 0) {
                System.out.println("Please choose a number between 0 and " + maxValue + "!");
                return false;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter a number!");
            return false;
        }
        return true;
    }

    /**
     * Calculates exchanges possibilities for 1 farmer(player) with 1 given stock, stores the result in a map.
     *
     * @param actFarmer              actual farmer(player) whose round it is
     * @param stockToProvideExchange the given stock to be checked for exchanges
     * @return Map containing all possible changes of the actual player with given stock
     */
    private Map<Animal, Map<Animal, Double>> getPossibleChanges(Farmer actFarmer, Map<Animal, Integer> stockToProvideExchange) {
        actFarmer.clearActualPossibleChanges();
        Map<Animal, Integer> actStock = actFarmer.getAnimalStock();
        for (Animal actAnimal : actStock.keySet()) {
            if (actStock.get(actAnimal) >= 1) {
                Map<Animal, Double> actExchangeTable = actAnimal.changeableTo();
                Map<Animal, Double> revisedExchangeTable = new HashMap<>();
                for (Animal excAnimal : actExchangeTable.keySet()) {
                    double exchangeRate = actExchangeTable.get(excAnimal);
                    boolean available = checkAvailability(excAnimal, exchangeRate, stockToProvideExchange);
                    if (available && (exchangeRate < 1.0 && actStock.get(actAnimal) >= 1 / exchangeRate || exchangeRate >= 1.0)) {
                        revisedExchangeTable.put(excAnimal, exchangeRate);
                    }
                }
            }
        return actFarmer.getActualPossibleChanges();
    }

    /**
     * Checks availability of the given number of given animal in the given stock.
     *
     * @param animal       type of animal whose availabilty is to be checked
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

    private void transactDiceRoll(Farmer actFarmer) {
        Animal result1 = actFarmer.rollDice(dice1);
        printDiceResult(result1);
        Animal result2 = actFarmer.rollDice(dice2);
        printDiceResult(result2);
        evaluateDiceResult(actFarmer, result1, result2);
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
        Map<Animal, Integer> farmerAnimals = actFarmer.getAnimalStock();
        int animalCounter = (int) farmerAnimals.keySet().stream()
                .filter(key -> !key.equals(new SmallDog()) && !key.equals(new BigDog()) && farmerAnimals.get(key) >= 1).count();
        return animalCounter == 5;
    }

    private void printDiceResult(Animal result) {
        System.out.println("The result of the dice rolled is:  " + result.getClass().getSimpleName());
    }

    private void askToContinue() {
        System.out.println("Shall we continue? (y/n)");
        String input;
        do {
            input = scan.next();
            if ("y".equals(input.toLowerCase())) {
                continue;
            } else if ("n".equals(input.toLowerCase())){
                System.exit(0);
            } else {
                System.out.println("Please enter y or n!");
            }
        } while (!"y".equals(input.toLowerCase()));
    }
}
