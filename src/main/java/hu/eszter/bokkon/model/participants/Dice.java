package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.List;

public class Dice {

    private final List<Animal> diceSides;

    public Dice(List<Animal> diceSides) {
        this.diceSides = diceSides;
    }

    public List<Animal> getDiceSides() {
        return diceSides;
    }
}
