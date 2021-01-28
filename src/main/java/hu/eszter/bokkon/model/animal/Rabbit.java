package hu.eszter.bokkon.model.animal;

import java.util.HashMap;
import java.util.Map;

public final class Rabbit implements Animal {

    private final int id;

    public Rabbit() {
        this.id = 47;
    }

    @Override
    public Map<Animal, Integer> changeableTo() {
        return new HashMap<>();
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
        Rabbit rabbit = (Rabbit) obj;
        return (rabbit.id == this.id);
    }
}
