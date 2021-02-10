package hu.eszter.bokkon.model.animal;

import java.util.Map;

public final class Fox implements Animal {

    private final int id;

    public Fox() {
        this.id = 59;
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
        Fox fox = (Fox) obj;
        return (fox.id == this.id);
    }

    @Override
    public Map<Animal, Double> changeableTo() {
        return null;
    }
}
