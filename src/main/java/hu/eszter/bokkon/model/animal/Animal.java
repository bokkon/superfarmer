package hu.eszter.bokkon.model.animal;

import java.util.Map;

public interface Animal {

    /**
     * @return a map that represents the exchangeTable for 1 type of animal: it shows what a farmer(player) can get
     * for an animal type. The value of the map can have a positive value lower, than 1, that refers to that multiple
     * animals can be exchanged to 1.
     */
    Map<Animal, Double> changeableTo();

}
