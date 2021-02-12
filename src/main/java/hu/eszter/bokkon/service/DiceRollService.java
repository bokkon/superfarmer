package hu.eszter.bokkon.service;

import hu.eszter.bokkon.model.animal.*;
import hu.eszter.bokkon.model.participants.Die;
import hu.eszter.bokkon.model.participants.Farmer;
import hu.eszter.bokkon.model.participants.StockOrganizer;

import java.util.Map;

public class DiceRollService {

    private final Die dice1;
    private final Die dice2;

    public DiceRollService() {
        this.dice1 = Initializer.createDice(new Sheep(), new Cow(), new Wolf());
        this.dice2 = Initializer.createDice(new Pig(), new Horse(), new Fox());
    }

    public void transactDiceRoll(Farmer actFarmer, StockOrganizer animalBaseStock) {
        Animal result1 = actFarmer.rollDice(dice1);
        printDiceResult(result1);
        Animal result2 = actFarmer.rollDice(dice2);
        printDiceResult(result2);
        evaluateDiceResult(actFarmer, result1, result2, animalBaseStock);
    }

    /**
     * Analyses the dice results. Examines whether the results of the 2 dice match and acts accordingly.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     * @param dieResult1 result of Die I.
     * @param dieResult2 result of Die II.
     */
    private void evaluateDiceResult(Farmer actFarmer, Animal dieResult1, Animal dieResult2, StockOrganizer animalBaseStock) {
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
    private void check1Die(Farmer actFarmer, Animal oneDieResult, StockOrganizer animalBaseStock) {
        Animal fox = new Fox();
        Animal smallDog = new SmallDog();
        Animal wolf = new Wolf();
        Animal bigDog = new BigDog();
        Animal rabbit = new Rabbit();
        if (oneDieResult.equals(fox) && actFarmer.getAnimalStock().get(smallDog) >= 1) {
            actFarmer.removeAnimals(smallDog, 1);
            animalBaseStock.addAnimals(smallDog, 1);
            System.out.println("The fox has come! 1 small dog was taken from " + actFarmer.getName() + "'s stock.");
        } else if (oneDieResult.equals(fox) && actFarmer.getAnimalStock().get(smallDog) == 0 && actFarmer.getAnimalStock().get(rabbit) >= 1) {
            animalBaseStock.addAnimals(rabbit, actFarmer.getAnimalStock().get(rabbit));
            actFarmer.setOneAnimalCountToZero(rabbit);
            System.out.println("The fox has come! All rabbits were taken from " + actFarmer.getName() + "'s stock.");
        } else if (oneDieResult.equals(wolf) && actFarmer.getAnimalStock().get(bigDog) >= 1) {
            actFarmer.removeAnimals(bigDog, 1);
            animalBaseStock.addAnimals(bigDog, 1);
            System.out.println("The wolf has come! 1 big dog was taken from " + actFarmer.getName() + "'s stock.");
        } else if (oneDieResult.equals(wolf) && actFarmer.getAnimalStock().get(bigDog) == 0) {
            returnAllAnimalsExceptHorsesAndSmallDogs(actFarmer, animalBaseStock);
        } else if (!oneDieResult.equals(fox) && !oneDieResult.equals(wolf)) {
            receiveAnimalsBy1Die(actFarmer, oneDieResult, animalBaseStock);
        }
    }

    /**
     * Processes the return of all animals from the stock of the actual player except for horses and small dogs.
     *
     * @param actFarmer the farmer(actual player) whose round it is
     */
    private void returnAllAnimalsExceptHorsesAndSmallDogs(Farmer actFarmer, StockOrganizer animalBaseStock) {
        Animal horse = new Horse();
        Animal smallDog = new SmallDog();
        for (Animal actAnimal : actFarmer.getAnimalStock().keySet()) {
            if (!actAnimal.equals(horse) || !actAnimal.equals(smallDog)) {
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
    private void receiveAnimalsBy1Die(Farmer actFarmer, Animal dieResult, StockOrganizer animalBaseStock) {
        int howManyBase = animalBaseStock.getAnimalStock().get(dieResult);
        int howManyFarmer = actFarmer.getAnimalStock().get(dieResult);
        int actHowMany = Math.min(howManyBase, ((howManyFarmer + 1) / 2));
        if (actHowMany >= 1) {
            actFarmer.addAnimals(dieResult, actHowMany);
            animalBaseStock.removeAnimals(dieResult, actHowMany);
        }
        System.out.println(actFarmer.getName() + " received " + actHowMany + " " + dieResult.getClass().getSimpleName() + (actHowMany == 0 || actHowMany > 1 ? "s" : "") + " from the base stock.");
    }

    /**
     * Checks if the actual farmer(player) whose round it is, whether or no has 5 different type of animals
     * which are not dogs.
     *
     * @param actFarmer is the farmer(player) whose round it is
     * @return integer value how many animal types the farmer has except for dogs
     */
    private boolean checkWin(Farmer actFarmer) {
        Map<Animal, Integer> farmerAnimals = actFarmer.getAnimalStock();
        int animalCounter = (int) farmerAnimals.keySet().stream()
                .filter(key -> !key.equals(new SmallDog()) && !key.equals(new BigDog()) && farmerAnimals.get(key) >= 1).count();
        return animalCounter == 5;
    }

    private void printDiceResult(Animal result) {
        System.out.println("The result of the dice rolled is:  " + result.getClass().getSimpleName());
    }
}
