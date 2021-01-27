package hu.eszter.bokkon;

import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.service.Util;

import java.util.Arrays;

public class Main {

    public static void main (String[] args) {

        Game newGame = new Game();
        newGame.init();

//        Util util = new Util();
//        util.printMapInt(newGame.getAnimalStock().getLiveStock());
//
//        Farmer farmer1 = newGame.getFarmers().get(0);
//        System.out.println("Farmer's stock:");
//        util.printMapInt(farmer1.getFarmerLiveStock());
//        System.out.println();
//
//        farmer1.addAnimals(Arrays.asList("Rabbit", "Sheep", "SmallDog", "Sheep", "Pig", "Horse", "Pig", "Pig"));
//        System.out.println("Farmer's stock:");
//        util.printMapInt(farmer1.getFarmerLiveStock());
//        System.out.println();
//
//        System.out.println("Possible changes for " + farmer1.getName() + ": ");
//        util.printMapStr(newGame.getPossibleChanges(farmer1));
    }
}
