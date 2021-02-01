package hu.eszter.bokkon;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.service.Util;

import java.util.Map;

public class Main {

    public static void main (String[] args) {

        Game newGame = new Game();
        newGame.init();

        //TODO delete this later
        Farmer player = newGame.getFarmers().get(0);
        Map<Animal, Integer> stock = newGame.getAnimalStock().getLiveStock();
        Map<Animal, Integer> plStock = player.getFarmerLiveStock();
        plStock.put(new Cow(), 2);
        plStock.put(new Rabbit(), 3);
        plStock.put(new SmallDog(), 1);
        plStock.put(new Pig(), 2);
        plStock.put(new Sheep(), 1);
        Util util = new Util();
        util.printSimpleChangesMap(newGame. getPossibleChanges(player));

//        newGame.getDice1().getDiceSides().forEach( a -> System.out.println(a.getClass().getSimpleName()));
    }
}
