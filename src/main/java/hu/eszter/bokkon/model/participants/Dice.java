package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dice {

    private final List<Animal> diceSides = new ArrayList<>(12);

    public Dice(List<Animal> diceSides) {
        if (diceSides.size() == 12) {
            this.diceSides.addAll(diceSides);
        }
    }

    public List<Animal> getDiceSides() {
        return Collections.unmodifiableList(diceSides);
    }
}
