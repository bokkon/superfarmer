package hu.eszter.bokkon.model.animal;

import java.util.HashMap;
import java.util.Map;

public final class Pig implements Animal {

    private final int id;

    public Pig() {
        this.id = 43;
    }

    @Override
    public Map<Animal, Integer> changeableTo() {
        Map<Animal, Integer> result = new HashMap<>();
        result.put(new Sheep(), 2);
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
        Pig pig = (Pig) obj;
        return (pig.id == this.id);
    }
}
