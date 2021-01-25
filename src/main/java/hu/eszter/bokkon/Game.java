package hu.eszter.bokkon;

import hu.eszter.bokkon.model.participants.AnimalStock;
import hu.eszter.bokkon.model.participants.Dice;
import hu.eszter.bokkon.model.participants.Farmer;

public class Game {

    private AnimalStock animalStock;
    private final Dice dice1;
    private final Dice dice2;
    private Farmer player1;
    private Farmer player2;

    public Game(AnimalStock animalStock, Dice dice1, Dice dice2, Farmer player1, Farmer player2) {
        this.animalStock = animalStock;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.player1 = player1;
        this.player2 = player2;
    }
}
