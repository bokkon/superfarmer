package hu.eszter.bokkon.model.animal;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Animal {

    private static final AtomicInteger count = new AtomicInteger(0);
    protected final int id;

    public Animal() {
        this.id = count.incrementAndGet();
    }

    public int getId() {
        return id;
    }

}
