package hu.eszter.bokkon.model.animal;

import java.util.HashMap;
import java.util.Map;

public final class Cow implements Animal {

    private final int id;

    public Cow() {
        this.id = 37;
    }

    @Override
    public Map<Animal, Double> changeableTo() {
        Map<Animal, Double> result = new HashMap<>();
        result.put(new BigDog(), 1.0);
        result.put(new Pig(), 3.0);
        result.put(new Horse(), 1.0/2);
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
        Cow cow = (Cow) obj;
        return (cow.id == this.id);
    }
}
