package hu.eszter.bokkon.model.animal;

import java.util.HashMap;
import java.util.Map;

public final class Horse implements Animal {

    private final int id;

    public Horse() {
        this.id = 41;
    }

    @Override
    public Map<Animal, Double> changeableTo() {
        Map<Animal, Double> result = new HashMap<>();
        result.put(new Cow(), 2.0);
        return result;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Horse horse = (Horse) obj;
        return (horse.id == this.id);
    }
}
