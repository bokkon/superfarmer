package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.Animal;

import java.security.SecureRandom;
import java.util.List;

public class Die {

    private final List<Animal> diceSides;
    private final SecureRandom random = new SecureRandom();

    public Die(List<Animal> diceSides) {
        if (diceSides.size() != 12) {
            throw new IllegalArgumentException("The dice must have 12 sides!");
        }
        this.diceSides = diceSides;
    }

    public Animal getRandomSide() {
        int randomNo = random.nextInt(12);
        return diceSides.get(randomNo);
    }
}
