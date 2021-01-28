package hu.eszter.bokkon.model.animal;

import java.util.HashMap;
import java.util.Map;

public final class SmallDog implements ProtectorAnimal {

    private final int id;

    public SmallDog() {
        this.id = 59;
    }

    @Override
    public Map<Animal, Integer> changeableTo() {
        Map<Animal, Integer> result = new HashMap<>();
        result.put(new Sheep(), 1);
        return result;
    }

    @Override
    public void protect() {

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
        SmallDog smallDoggo = (SmallDog) obj;
        return (smallDoggo.id == this.id);
    }
}
