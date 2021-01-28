package hu.eszter.bokkon.model.animal;

import java.util.HashMap;
import java.util.Map;

public final class Sheep implements Animal {

    private final int id;

    public Sheep() {
        this.id = 53;
    }

    @Override
    public Map<Animal, Integer> changeableTo() {
        Map<Animal, Integer> result = new HashMap<>();
        result.put(new Rabbit(), 6);
        result.put(new SmallDog(), 1);
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
        Sheep sheep = (Sheep) obj;
        return (sheep.id == this.id);
    }
}
