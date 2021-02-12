package hu.eszter.bokkon;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.model.participants.StockOrganizer;
import hu.eszter.bokkon.service.DiceRollService;
import hu.eszter.bokkon.service.ExchangeService;
import hu.eszter.bokkon.service.Initializer;
import hu.eszter.bokkon.service.Util;

import java.util.*;

public class Game {

    private StockOrganizer animalBaseStock;
    private List<Farmer> farmers;
    private boolean thereIsAWinner = false;
    private final Scanner scan;
    private final ExchangeService exchangeService;
    private final DiceRollService diceRollService;

    public Game() {
        this.animalBaseStock = Initializer.createAnimalStock();
        this.farmers = new ArrayList<>();
        this.exchangeService = Initializer.setUpExchangeService();
        this.diceRollService = Initializer.setUpDiceRollService();
        scan = new Scanner(System.in);
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
    public void run() {
        Util.displayAllStocks(animalBaseStock.getAnimalStock(), farmers);
        while (!thereIsAWinner) {
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
            System.out.println(exchangeService.transactExchange(actFarmer, animalBaseStock));
            Util.displayAllStocks(animalBaseStock.getAnimalStock(), farmers);
            if (checkWin(actFarmer)) {
                thereIsAWinner = true;
                System.out.println("Congratulations! ".toUpperCase() + Util.getAnsiBrightRed() + actFarmer.getName() + Util.getReturnColour()
                        + " you are the " + Util.getAnsiBrightGreen() + "Superfarmer!" + Util.getReturnColour());
                scan.close();
                return;
            }
            diceRollService.transactDiceRoll(actFarmer, animalBaseStock);
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
