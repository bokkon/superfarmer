package hu.eszter.bokkon.model.participants;

import java.util.List;

public class Dice {

    private final List<String> diceSides;

    public Dice(List<String> diceSides) {
        this.diceSides = diceSides;
    }

    public List<String> getDiceSides() {
        return diceSides;
    }
}
