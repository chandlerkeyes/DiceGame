package com.Dice;

public class DiceGame {
    private GVdie d1;
    private GVdie d2;
    private GVdie d3;

    private int credits;
    private int guess;
    private String message;

    public DiceGame() {
        //dice objects
        d1 = new GVdie();
        d2 = new GVdie();
        d3 = new GVdie();
        credits = 100;
        message = "Welcome to Dice Game";
        //setting dice to blank
        d1.setBlank();
        d2.setBlank();
        d3.setBlank();
    }
    //accessor methods
    public String getMessage() {
        return message;
    }
    public int getCredits() {
        return credits;
    }
    public GVdie getDie (int num) {
        switch (num) {
            case 1: return d1;
            case 2: return d2;
            case 3: return d3;
            default: return new GVdie();
        }
    }

    //Mutator methods
    public void setGuess(int g) {
        guess = g;
    }
    public void restart() {
        credits = 100;
        message = "Welcome to Dice Game";
        d1.setBlank();
        d2.setBlank();
        d3.setBlank();
    }
    public void playGame() {
        rollDice();

        if(isTriplets()) {
            credits += 50;
            message = "Three of a kind";
        }
        else if(isDoubles()){
            credits += 20;
            message = "A pair!";
        }
        else if (wasNumberRolled()) {
            credits += 10;
            message = "Only one match";
        }
        else {
            credits = credits - 10;
            message = "No match - lose!";
        }

    }
    //private helper methods
    private void rollDice() {
        d1.roll();
        d2.roll();
        d3.roll();
    }
    private boolean wasNumberRolled() {
        if ((d1.getValue() == guess) || (d2.getValue() == guess) ||
                (d3.getValue() == guess)) {
            return true;
        }
        else {
            return false;
        }
    }
    private boolean isDoubles() {
        if((d1.getValue() == guess && d2.getValue() == guess) ||
                (d1.getValue() == guess && d3.getValue() == guess) ||
                (d1.getValue() == guess && d3.getValue() == guess) ) {
            return true;
        }
        else {
            return false;
        }
    }
    private boolean isTriplets() {
        if(d1.getValue() == guess && d2.getValue() ==
                guess && d3.getValue() == guess) {
            return true;
        }
        else {
            return false;
        }
    }
}