package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.Animal;
import hu.eszter.bokkon.model.participants.Farmer;

import java.util.*;

public class Util {

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
                System.out.print((line > 9 ? "" : " ") + line++ + ".) ");
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
    }

    /**
     * Displays all stocks of 1 game in horizontal, including the base animal stock and all farmers' stock.
     */
    public static void displayAllStocks(Map<Animal, Integer> baseStock, List<Farmer> allFarmers) {
        System.out.println();
        System.out.print("Base Stock" + getSpaces(14, 0));
        allFarmers.forEach( f -> System.out.print(f.getName() + getSpaces(24, f.getName().length())));
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
        System.out.println();
    }

    private static void printEntrySet(String name, int count) {
        System.out.print(name + getSpaces(9,name.length()) + ": "
                + (String.valueOf(count).length() == 1 ? " " : "") + count + getSpaces(11,0));
    }


    private static String getSpaces(int maxLength, int wordLength) {
        return String.join("", Collections.nCopies(maxLength - wordLength, " "));
    }

    public static void startMessage() {
        System.out.println(" _____    __    __    ______   _______  ______    ______    .        ______    ___      ___  _______  ______");
        System.out.println("||   ||   ||    ||   ||    \\\\  ||       ||    \\\\  ||        /\\       ||    \\\\  ||\\\\    //||  ||       ||    \\\\");
        System.out.println("\\\\        ||    ||   ||    //  ||       ||    //  ||       //\\\\      ||    //  || \\\\  // ||  ||       ||    // ");
        System.out.println(" ----     ||    ||   || ---    ||----   || ---    ||----  //--\\\\     || ---    ||  \\\\//  ||  ||----   || ---");
        System.out.println("     \\\\   ||    ||   ||        ||       ||  \\\\    ||     //    \\\\    ||  \\\\    ||   ---  ||  ||       ||  \\\\");
        System.out.println("||   ||   \\\\    //   ||        ||       ||   \\\\   ||    //      \\\\   ||   \\\\   ||        ||  ||       ||   \\\\");
        System.out.println(" -----      ----     --        -------  --    --  --   --        --  --    --  --        --  -------  --    --");
    }
}
