package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.Animal;
import hu.eszter.bokkon.model.participants.Farmer;

import java.util.*;

public class Util {

    private static final String ANSI_BRIGHT_BLUE   = "\u001B[94m";
    private static final String ANSI_BLUE   = "\u001B[34m";
    private static final String ANSI_CYAN   = "\u001B[36m";
    private static final String ANSI_BRIGHT_CYAN   = "\u001B[96m";
    private static final String RETURN_COLOUR = "\u001B[0m";
    private static final String ANSI_BG_PURPLE = "\u001B[45m";
    private static final String ANSI_BG_CYAN   = "\u001B[46m";
    private static final String ANSI_BRIGHT_BLACK = "\u001B[97m";
    private static final String ANSI_BRIGHT_RED    = "\u001B[91m";
    private static final String ANSI_BRIGHT_GREEN  = "\u001B[92m";

    public static String getReturnColour() {
        return RETURN_COLOUR;
    }

    public static String getAnsiBrightRed() {
        return ANSI_BRIGHT_RED;
    }

    public static String getAnsiBrightGreen() {
        return ANSI_BRIGHT_GREEN;
    }

    /**
     * Displays all the possible exchanges between 2 stocks: the actual farmer's animal stock and another animal stock.
     *
     * @param possibleChanges is a map data structure storing the possible exchanges between 2 animal stocks
     */
    public static void printPossibleChangesMap(Map<Animal, Map<Animal, Double>> possibleChanges) {
        int line = 1;
        for (Animal exchangeAnimal : possibleChanges.keySet()) {
            String animalName = exchangeAnimal.getClass().getSimpleName();
            Map<Animal, Double> actMap = possibleChanges.get(exchangeAnimal);
            for (Animal returnAnimal : actMap.keySet()) {
                System.out.print(ANSI_BG_PURPLE + ANSI_BRIGHT_BLACK + (line > 9 ? "" : " ") + line++ + ".) ");
                double count = actMap.get(returnAnimal);
                if (count >= 1.0) {
                    System.out.println("1 " + animalName + getSpaces(8, animalName.length()) + " ===>   "
                            + (int) count + " " + returnAnimal.getClass().getSimpleName() + (count == 1 ? "" : "s"));
                } else if (count > 0.0) {
                    System.out.println((int) (1 / count) + " " + animalName + ((int) (1 / count) == 1 ? " " : "s")
                            + getSpaces(7, animalName.length())
                            + " ===>   1 " + returnAnimal.getClass().getSimpleName());
                }
            }
        }
        System.out.println(RETURN_COLOUR);
    }

    /**
     * Displays all stocks of 1 game in horizontal, including the base animal stock and all farmers' stock.
     */
    public static void displayAllStocks(Map<Animal, Integer> baseStock, List<Farmer> allFarmers) {
        System.out.println();
        System.out.print(ANSI_BG_CYAN + ANSI_BRIGHT_BLACK + " Base Stock" + getSpaces(14, 0));
        allFarmers.forEach( f -> System.out.print(" " + f.getName() + getSpaces(24, f.getName().length())));
        System.out.println();
        System.out.println(String.join("", Collections.nCopies(24*(allFarmers.size()+1)-10, "-")));
        for (Animal actAnimal: baseStock.keySet()) {
            String actName = actAnimal.getClass().getSimpleName();
            int count = baseStock.get(actAnimal);
            printEntrySet(actName, count);
            for (int i = 0; i < allFarmers.size(); i++) {
                Map<Animal, Integer> actMap = allFarmers.get(i).getFarmerLiveStock();
                printEntrySet(actName, (actMap.get(actAnimal) == null ? 0 : actMap.get(actAnimal)));
                if (i == allFarmers.size() - 1) {
                    System.out.println();
                }
            }
        }
        System.out.println();
        System.out.println(RETURN_COLOUR);
    }

    private static void printEntrySet(String name, int count) {
        System.out.print(" " + name + getSpaces(9,name.length()) + ": "
                + (String.valueOf(count).length() == 1 ? " " : "") + count + getSpaces(11,0));
    }


    private static String getSpaces(int maxLength, int wordLength) {
        return String.join("", Collections.nCopies(maxLength - wordLength, " "));
    }

    public static void startMessage() {
        System.out.println(ANSI_BRIGHT_BLUE + " _____    __    __    ______   _______  ______    ______    .        ______    ___      ___  _______  ______"  + RETURN_COLOUR);
        System.out.println(ANSI_BRIGHT_BLUE + "||   ||   ||    ||   ||    \\\\  ||       ||    \\\\  ||        /\\       ||    \\\\  ||\\\\    //||  ||       ||    \\\\"  + RETURN_COLOUR);
        System.out.println(ANSI_BLUE + "\\\\        ||    ||   ||    //  ||       ||    //  ||       //\\\\      ||    //  || \\\\  // ||  ||       ||    // "  + RETURN_COLOUR);
        System.out.println(ANSI_BLUE + " ----     ||    ||   || ---    ||----   || ---    ||----  //--\\\\     || ---    ||  \\\\//  ||  ||----   || ---"  + RETURN_COLOUR);
        System.out.println(ANSI_CYAN + "     \\\\   ||    ||   ||        ||       ||  \\\\    ||     //    \\\\    ||  \\\\    ||   ---  ||  ||       ||  \\\\"  + RETURN_COLOUR);
        System.out.println(ANSI_CYAN + "||   ||   \\\\    //   ||        ||       ||   \\\\   ||    //      \\\\   ||   \\\\   ||        ||  ||       ||   \\\\"  + RETURN_COLOUR);
        System.out.println(ANSI_BRIGHT_CYAN + " -----      ----     --        -------  --    --  --   --        --  --    --  --        --  -------  --    --" + RETURN_COLOUR);
    }


}
