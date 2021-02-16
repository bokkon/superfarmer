package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.Die;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.model.participants.StockProvider;

public class DiceRollService {

    private final Die dice1;
    private final Die dice2;

    public DiceRollService() {
        this.dice1 = Initializer.createDice(Animal.SHEEP, Animal.COW, Animal.WOLF);
        this.dice2 = Initializer.createDice(Animal.PIG, Animal.HORSE, Animal.FOX);
    }

    public void transactDiceRoll(Farmer actFarmer, StockProvider animalBaseStock) {
        Animal result1 = actFarmer.rollDice(dice1);
        Util.printDiceResult(result1);
        Animal result2 = actFarmer.rollDice(dice2);
        Util.printDiceResult(result2);
        evaluateDiceResult(actFarmer, result1, result2, animalBaseStock);
    }

    /**
     * Analyses the dice results. Examines whether the results of the 2 dice match and acts accordingly.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     * @param dieResult1 result of Die I.
     * @param dieResult2 result of Die II.
     */
    private void evaluateDiceResult(Farmer actFarmer, Animal dieResult1, Animal dieResult2, StockProvider animalBaseStock) {
        if (dieResult1.equals(dieResult2)) {
            int howMany1Base = animalBaseStock.getAnimalStock().get(dieResult1);
            int howMany1Farmer = actFarmer.getAnimalStock().get(dieResult1);
            int actHowMany = Math.min(howMany1Base, (howMany1Farmer + 2) / 2);
            actFarmer.addAnimals(dieResult1, actHowMany);
            animalBaseStock.removeAnimals(dieResult1, actHowMany);
            System.out.println(actFarmer.getName() + " received " + actHowMany + " " + dieResult1.getClass().getSimpleName() + (actHowMany > 1 ? "s" : "") + " from the base stock.");
        } else {
            check1Die(actFarmer, dieResult1, animalBaseStock);
            check1Die(actFarmer, dieResult2, animalBaseStock);
        }
    }

    /**
     * Processes the result of 1 die individually.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     * @param oneDieResult result of 1 die
     */
    private void check1Die(Farmer actFarmer, Animal oneDieResult, StockProvider animalBaseStock) {
        if (oneDieResult.equals(Animal.FOX) && actFarmer.getAnimalStock().get(Animal.SMALLDOG) >= 1) {
            actFarmer.removeAnimals(Animal.SMALLDOG, 1);
            animalBaseStock.addAnimals(Animal.SMALLDOG, 1);
            System.out.println("The fox has come! 1 small dog was taken from " + actFarmer.getName() + "'s stock.");
        } else if (oneDieResult.equals(Animal.FOX) && actFarmer.getAnimalStock().get(Animal.SMALLDOG) == 0
                && actFarmer.getAnimalStock().get(Animal.RABBIT) >= 1) {
            animalBaseStock.addAnimals(Animal.RABBIT, actFarmer.getAnimalStock().get(Animal.RABBIT));
            actFarmer.setOneAnimalCountToZero(Animal.RABBIT);
            System.out.println("The fox has come! All rabbits were taken from " + actFarmer.getName() + "'s stock.");
        } else if (oneDieResult.equals(Animal.WOLF) && actFarmer.getAnimalStock().get(Animal.BIGDOG) >= 1) {
            actFarmer.removeAnimals(Animal.BIGDOG, 1);
            animalBaseStock.addAnimals(Animal.BIGDOG, 1);
            System.out.println("The wolf has come! 1 big dog was taken from " + actFarmer.getName() + "'s stock.");
        } else if (oneDieResult.equals(Animal.WOLF) && actFarmer.getAnimalStock().get(Animal.BIGDOG) == 0) {
            returnAllAnimalsExceptHorsesAndSmallDogs(actFarmer, animalBaseStock);
        } else if (oneDieResult.getAnimalType() != AnimalType.ATTACKER) {
            receiveAnimalsBy1Die(actFarmer, oneDieResult, animalBaseStock);
        }
    }

    /**
     * Processes the return of all animals from the stock of the actual player except for horses and small dogs.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     */
    private void returnAllAnimalsExceptHorsesAndSmallDogs(Farmer actFarmer, StockProvider animalBaseStock) {
        for (Animal actAnimal : actFarmer.getAnimalStock().keySet()) {
            if (!actAnimal.equals(Animal.HORSE) && !actAnimal.equals(Animal.SMALLDOG)) {
                if ( actFarmer.getAnimalStock().get(actAnimal) != 0 ) {
                    animalBaseStock.addAnimals(actAnimal, actFarmer.getAnimalStock().get(actAnimal));
                }
            }
        }
        System.out.println("The wolf has come! Your farm is devastated. Keep a big dog!");
        actFarmer.setAnimalsCountToZero();
    }

    /**
     * Executes the receipt of animals according to the result of 1 die.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     * @param dieResult result of 1 die
     */
    private void receiveAnimalsBy1Die(Farmer actFarmer, Animal dieResult, StockProvider animalBaseStock) {
        int howManyBase = animalBaseStock.getAnimalStock().get(dieResult);
        int howManyFarmer = actFarmer.getAnimalStock().get(dieResult);
        int actHowMany = Math.min(howManyBase, ((howManyFarmer + 1) / 2));
        if (actHowMany >= 1) {
            actFarmer.addAnimals(dieResult, actHowMany);
            animalBaseStock.removeAnimals(dieResult, actHowMany);
        }
        System.out.println(actFarmer.getName() + " received " + actHowMany + " " + dieResult.toString().toLowerCase() + (actHowMany == 0 || actHowMany > 1 ? "s" : "") + " from the base stock.");
    }

}
