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
    private List<Farmer> farmers;
    private boolean thereIsAWinner = false;
    private final Scanner scan;

    public Game() {
        this.animalBaseStock = Initializer.createAnimalStock();
        this.dice1 = Initializer.createDice(new Sheep(), new Cow(), new Wolf());
        this.dice2 = Initializer.createDice(new Pig(), new Horse(), new Fox());
        this.farmers = new ArrayList<>();
        scan = new Scanner(System.in);
    }

    public List<Farmer> getFarmers() {
        return farmers;
    }

    public AnimalBaseStock getAnimalBaseStock() {
        return animalBaseStock;
    }

    public void init() {
        Util.startMessage();
        System.out.println("");
        System.out.println("WELCOME!");
        System.out.println("");
        int howManyPlayers = getHowManyPlayersInput();
        for (int i = 0; i < howManyPlayers; i++) {
            this.farmers.add(Initializer.createPlayer(getFarmerNameInput(i + 1)));
        }
        System.out.println("");
        System.out.println("THE GAME BEGINS !!! \n");
    }

    private int getHowManyPlayersInput() {
        System.out.println("Please choose how many players will participate! ( 2 to 6 ) ");
        String input;
        do {
            input = scan.next();
            if ("q".equals(input.toLowerCase())) {
                System.exit(0);
            }
        } while (!checkInputNumber(input, 2,6));
        return Integer.parseInt(input);
    }

    private String getFarmerNameInput(int idx) {
        String input;
        do {
            System.out.println("Please enter the " + idx + ".name: ");
            input = scan.next();
            if ("q".equals(input.toLowerCase())) {
                System.exit(0);
            }
            if (!input.matches("^[A-Za-z]*$")) {
                System.out.println("Please use letters only!");
            }
            if (input.length() > 20) {
                System.out.println("Please choose a shortest name! (Maximum 20 characters)");
            }
        } while (!(input.matches("^[A-Za-z]*$") && 20 >= input.length()));
        System.out.println("Thank you! You are about to play Super Farmer.");
        System.out.println("");
        return input;
    }

    /**
     * Start game.
     */
    //TODO refactor when farmers will be able to exchange among each other, empty main stock will not be a condiition to
    // end the game
    public void run() {
        Util.displayAllStocks(animalBaseStock.getAnimalStock(), farmers);
        while (!thereIsAWinner || animalBaseStock.isEmpty()) {
            doRound();
            if (animalBaseStock.getAnimalStock().values().stream().mapToInt(v -> v).sum() == 0) {
                System.out.println("Main stock is empty! The game is over! No one became the super farmer.");
                return;
            }
        }
    }

    /**
     * Run 1 round of the game, going through all farmers(players) once.
     */
    private void doRound() {
        for (Farmer actFarmer : farmers) {
            displayActualFarmersName(actFarmer);
            System.out.println(transactExchange(actFarmer));
            Util.displayAllStocks(animalBaseStock.getAnimalStock(), farmers);
            if (checkWin(actFarmer)) {
                thereIsAWinner = true;
                System.out.println("Congratulations! ".toUpperCase() + Util.getAnsiBrightRed() + actFarmer.getName() + Util.getReturnColour()
                        + " you are the " + Util.getAnsiBrightGreen() + "Superfarmer!" + Util.getReturnColour());
                scan.close();
                return;
            }
            transactDiceRoll(actFarmer);
            Util.displayAllStocks(animalBaseStock.getAnimalStock(), farmers);
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


    /**
     * Processes the exchange for 1 farmer in 1 round.
     *
     * @param actFarmer is the farmer whose round is
     * @return String value containing whether the exchange took place.
     */
    //TODO refactor so that exchange can be possible between any farmers(players) too
    private String transactExchange(Farmer actFarmer) {
        Map<Animal, Map<Animal, Double>> possExchanges = getPossibleChanges(actFarmer, animalBaseStock.getAnimalStock());
        if (possExchanges.size() == 0) {
            return "There are no possible exchanges for " + actFarmer.getName() + " !";
        }
        System.out.println("Possible changes for " + actFarmer.getName() + ":\n");
        Util.printPossibleChangesMap(possExchanges);
        int countPossibilities = possExchanges.values().stream().mapToInt(Map::size).sum();
        int selectedExchange = getExchangeSelectionInput(actFarmer, 0, countPossibilities);
        return executeExchange(actFarmer, possExchanges, selectedExchange) ?
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

    /**
     * Processes the roll of 2 dice, and executes the consequent changes.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     */
    private void transactDiceRoll(Farmer actFarmer) {
        Animal result1 = actFarmer.rollDice(dice1);
        printDiceResult(result1);
        Animal result2 = actFarmer.rollDice(dice2);
        printDiceResult(result2);
        evaluateDiceResult(actFarmer, result1, result2);
    }

    /**
     * Analyses the dice results. Examines whether the results of the 2 dice match and acts accordingly.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     * @param dieResult1 result of Die I.
     * @param dieResult2 result of Die II.
     */
    private void evaluateDiceResult(Farmer actFarmer, Animal dieResult1, Animal dieResult2) {
        if (dieResult1.equals(dieResult2)) {
            int howMany1Base = animalBaseStock.getAnimalStock().get(dieResult1);
            int howMany1Farmer = actFarmer.getAnimalStock().get(dieResult1);
            int actHowMany = Math.min(howMany1Base, (howMany1Farmer + 2) / 2);
            actFarmer.addAnimals(dieResult1, actHowMany);
            animalBaseStock.removeAnimals(dieResult1, actHowMany);
            System.out.println(actFarmer.getName() + " received " + actHowMany + " " + dieResult1.getClass().getSimpleName() + (actHowMany > 1 ? "s" : "") + " from the base stock.");
        } else {
            check1Die(actFarmer, dieResult1);
            check1Die(actFarmer, dieResult2);
        }
    }

    /**
     * Processes the result of 1 die individually.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     * @param oneDieResult result of 1 die
     */
    private void check1Die(Farmer actFarmer, Animal oneDieResult) {
        Animal fox = new Fox();
        Animal smallDog = new SmallDog();
        Animal wolf = new Wolf();
        Animal bigDog = new BigDog();
        Animal rabbit = new Rabbit();
        if (oneDieResult.equals(fox) && actFarmer.getAnimalStock().get(smallDog) >= 1) {
            actFarmer.removeAnimals(smallDog, 1);
            animalBaseStock.addAnimals(smallDog, 1);
            System.out.println("The fox has come! 1 small dog was taken from " + actFarmer.getName() + "'s stock.");
        } else if (oneDieResult.equals(fox) && actFarmer.getAnimalStock().get(smallDog) == 0 && actFarmer.getAnimalStock().get(rabbit) >= 1) {
            animalBaseStock.addAnimals(rabbit, actFarmer.getAnimalStock().get(rabbit));
            actFarmer.setOneAnimalCountToZero(rabbit);
            System.out.println("The fox has come! All rabbits were taken from " + actFarmer.getName() + "'s stock.");
        } else if (oneDieResult.equals(wolf) && actFarmer.getAnimalStock().get(bigDog) >= 1) {
            actFarmer.removeAnimals(bigDog, 1);
            animalBaseStock.addAnimals(bigDog, 1);
            System.out.println("The wolf has come! 1 big dog was taken from " + actFarmer.getName() + "'s stock.");
        } else if (oneDieResult.equals(wolf) && actFarmer.getAnimalStock().get(bigDog) == 0) {
            returnAllAnimalsExceptHorsesAndSmallDogs(actFarmer);
        } else if (!oneDieResult.equals(fox) && !oneDieResult.equals(wolf)) {
            receiveAnimalsBy1Die(actFarmer, oneDieResult);
        }
    }

    /**
     * Processes the return of all animals from the stock of the actual player except for horses and small dogs.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     */
    private void returnAllAnimalsExceptHorsesAndSmallDogs(Farmer actFarmer) {
        Animal horse = new Horse();
        Animal smallDog = new SmallDog();
        for (Animal actAnimal : actFarmer.getAnimalStock().keySet()) {
            if (!actAnimal.equals(horse) || !actAnimal.equals(smallDog)) {
                if ( actFarmer.getAnimalStock().get(actAnimal) != 0 ) {
                    animalBaseStock.addAnimals(actAnimal, actFarmer.getAnimalStock().get(actAnimal));
                }
            }
        }
        System.out.println("The wolf has come! Your farm is devastated. Keep a big dog!");
        actFarmer.setAnimalsCountToZero();
    }

    /**
     * Executes the receipt of animals according to the result of 1 die.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     * @param dieResult result of 1 die
     */
    private void receiveAnimalsBy1Die(Farmer actFarmer, Animal dieResult) {
        int howManyBase = animalBaseStock.getAnimalStock().get(dieResult);
        int howManyFarmer = actFarmer.getAnimalStock().get(dieResult);
        int actHowMany = Math.min(howManyBase, ((howManyFarmer + 1) / 2));
        if (actHowMany >= 1) {
            actFarmer.addAnimals(dieResult, actHowMany);
            animalBaseStock.removeAnimals(dieResult, actHowMany);
        }
        System.out.println(actFarmer.getName() + " received " + actHowMany + " " + dieResult.getClass().getSimpleName() + (actHowMany == 0 || actHowMany > 1 ? "s" : "") + " from the base stock.");
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
            if ("n".equals(input.toLowerCase())) {
                System.exit(0);
            }
            if (!"y".equals(input.toLowerCase())) {
                System.out.println("Please enter y or n!");
            }
        } while (!"y".equals(input.toLowerCase()));
        System.out.println("");
    }
}
