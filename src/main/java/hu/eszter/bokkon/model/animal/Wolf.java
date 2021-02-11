package hu.eszter.bokkon.model.animal;

import java.util.Map;

public final class Wolf implements Animal {

    private final int id;

    public Wolf() {
        this.id = 61;
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
        Wolf wolf = (Wolf) obj;
        return (wolf.id == this.id);
    }

    @Override
    public Map<Animal, Double> changeableTo() {
        return null;
    }
}
