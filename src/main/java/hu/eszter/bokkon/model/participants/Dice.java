package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.Collections;
import java.util.List;

public class Dice {

    private final List<Animal> diceSides;

    public Dice(List<Animal> diceSides) {
        if (diceSides.size() != 12) {
            throw new IllegalArgumentException("The dice must have 12 sides!");
        }
        this.diceSides = diceSides;
    }

    public List<Animal> getDiceSides() {
        return Collections.unmodifiableList(diceSides);
    }
}
