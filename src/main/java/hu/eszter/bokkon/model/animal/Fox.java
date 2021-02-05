package hu.eszter.bokkon.model.animal;

import java.util.HashMap;
import java.util.Map;

public final class Fox implements AttackerAnimal {

    @Override
    public void attack() {

    }

    @Override
    public Map<Animal, Double> changeableTo() {
        return new HashMap<>();
    }
}
