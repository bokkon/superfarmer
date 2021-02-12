package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.Animal;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.model.participants.StockProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExchangeService {

    private final Scanner scan;

    public ExchangeService() {
        scan = new Scanner(System.in);
    }

    /**
     * Processes the exchange for 1 farmer in 1 round.
     *
     * @param actFarmer is the farmer whose round is
     * @return String value containing whether the exchange took place.
     */
    //TODO refactor so that exchange can be possible between any farmers(players) too
    public String transactExchange(Farmer actFarmer, StockProvider animalBaseStock) {
        Map<Animal, Map<Animal, Double>> possExchanges = getPossibleChanges(actFarmer, animalBaseStock.getAnimalStock());
        if (possExchanges.size() == 0) {
            return "There are no possible exchanges for " + actFarmer.getName() + " !";
        }
        System.out.println("Possible changes for " + actFarmer.getName() + ":\n");
        Util.displayPossibleExchanges(possExchanges);
        int countPossibilities = possExchanges.values().stream().mapToInt(Map::size).sum();
        int selectedExchange = getExchangeSelectionInput(actFarmer, 0, countPossibilities);
        return executeExchange(actFarmer, possExchanges, selectedExchange, animalBaseStock) ?
                "Exchange was successful!" : actFarmer.getName() + " decided not to exchange!";
    }


    /**
     * Asks input from the actual player which exchange to process
     *
     * @param actFarmer is the farmer or actual player whose round is
     * @param maxValue the number of maximum exchange possibilities, therefore the highest number to be able to choose
     * @return int value of the chosen exchange
     */
    private int getExchangeSelectionInput(Farmer actFarmer, int minValue, int maxValue) {
        System.out.println(actFarmer.getName() + " please pick an exchange! ( 1" + (maxValue == 1 ? "" : " to " + maxValue) + " ) Pick 0 if you don't wish to exchange.");
        String input;
        do {
            input = scan.next();
            if ("q".equals(input.toLowerCase())) {
                System.exit(0);
            }
        } while (!checkInputNumber(input, minValue, maxValue));
        return Integer.parseInt(input);
    }

    /**
     * Executes certain change. What it removes from 1 stock, it adds to the other.
     *
     * @param actFarmer is the farmer or actual player whose round is
     * @param possibleExchanges a map containing all exchange possibilities for 1 farmer in 1 round with base stock
     * @param numberOfSelected the selected exchange to process
     * @return boolean value whether the exchange took place or not
     */
    private boolean executeExchange(Farmer actFarmer, Map<Animal, Map<Animal, Double>> possibleExchanges, int numberOfSelected, StockProvider animalBaseStock) {
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
     * Validates the input required from the farmer(actual player)
     *
     * @param input    an input received from the player in String format
     * @param maxValue is the maximum value the input can have after converted into Integer
     * @return whether the input is valid
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkInputNumber(String input, int minValue, int maxValue) {
        try {
            int number = Integer.parseInt(input);
            if (number > maxValue || number < minValue) {
                System.out.println("Please choose a number between " + minValue + " and " + maxValue + "!");
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
     * @param actFarmer actual farmer(player) whose round it is
     * @param stockToProvideExchange the given stock to be checked for exchanges
     * @return Map containing all possible changes of the actual player with given stock
     */
    private Map<Animal, Map<Animal, Double>> getPossibleChanges(Farmer actFarmer, Map<Animal, Integer> stockToProvideExchange) {
        actFarmer.clearActualPossibleChanges();
        Map<Animal, Integer> actFarmerStock = actFarmer.getAnimalStock();
        for (Animal actFarmerAnimal : actFarmerStock.keySet()) {
            if (actFarmerStock.get(actFarmerAnimal) >= 1) {
                Map<Animal, Double> actExchangeTable = actFarmerAnimal.changeableTo();
                Map<Animal, Double> revisedExchangeTable = new HashMap<>();
                for (Animal exchangeAnimal : actExchangeTable.keySet()) {
                    double exchangeRate = actExchangeTable.get(exchangeAnimal);
                    boolean available = checkAvailability(exchangeAnimal, exchangeRate, stockToProvideExchange);
                    if (available && (exchangeRate < 1.0 && actFarmerStock.get(actFarmerAnimal) >= 1 / exchangeRate || exchangeRate >= 1.0)) {
                        revisedExchangeTable.put(exchangeAnimal, exchangeRate);
                    }
                    //if there are fewer animals in the base stock to provide according to the exchange rates, but there still are
                    if (!available && stockToProvideExchange.get(exchangeAnimal) >= 1) {
                        revisedExchangeTable.put(exchangeAnimal, (double)stockToProvideExchange.get(exchangeAnimal));
                    }
                } if (!revisedExchangeTable.isEmpty()) {
                    actFarmer.setActualPossibleChanges(actFarmerAnimal, revisedExchangeTable);
                }
            }
        }
        return actFarmer.getActualPossibleChanges();
    }

    /**
     * Checks the availability of a given animal in the given stock.
     *
     * @param animal       type of animal whose availabilty is to be checked
     * @param exchangeRate exchangeRate of animal referring to the number of animals to be checked
     *                     (if the value of the exchangeRate is lower than 1, it means that we need 1 of that animal)
     * @param stockToCheck animal stock to be checked
     * @return boolean value whether the animal is available in the stock
     */
    private boolean checkAvailability(Animal animal, double exchangeRate, Map<Animal, Integer> stockToCheck) {
        return (exchangeRate < 1.0) ? stockToCheck.get(animal) >= 1 : stockToCheck.get(animal) >= exchangeRate;
    }

}
