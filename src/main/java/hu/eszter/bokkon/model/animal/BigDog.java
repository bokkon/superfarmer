package hu.eszter.bokkon.model.animal;

import java.util.HashMap;
import java.util.Map;

public final class BigDog implements Animal {

    private final int id;

    public BigDog() {
        this.id = 31;
    }

    @Override
    public Map<Animal, Double> changeableTo() {
        Map<Animal, Double> result = new HashMap<>();
        result.put(new Cow(), 1.0);
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
        BigDog bigDoggy = (BigDog) obj;
        return (bigDoggy.id == this.id);
    }
}
