package hu.eszter.bokkon.model.animal;

import java.util.HashMap;
import java.util.Map;

public final class Wolf implements AttackerAnimal {

    @Override
    public void attack() {

    }

    @Override
    public Map<Animal, Integer> changeableTo() {
        return new HashMap<>();
    }
}
