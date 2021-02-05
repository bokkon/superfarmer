package hu.eszter.bokkon;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.service.Util;

public class Main {

    public static void main(String[] args) {

        Game newGame = new Game();
        Util.startMessage();
        newGame.init();
        //TODO delete later, filling the farmers' stock with animals is for test purposes
        newGame.getFarmers().get(0).addAnimals(new Rabbit(), 4);
        newGame.getFarmers().get(0).addAnimals(new Horse(), 1);
        newGame.getFarmers().get(0).addAnimals(new Sheep(), 10);
        newGame.getFarmers().get(1).addAnimals(new Cow(), 6);
        newGame.getFarmers().get(1).addAnimals(new Pig(), 3);
        newGame.getFarmers().get(1).addAnimals(new SmallDog(), 1);

        newGame.run();

    }
}
