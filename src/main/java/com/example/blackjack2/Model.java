package com.example.blackjack2;

import java.util.ArrayList;

public class Model implements Subscriber{

    private ArrayList<Card> cards;
    private ArrayList<ArrayList<Card>> playerHands;
    private ArrayList<Card> dealerHand;

    //the number of spots that can be played at the blackjack table
    private final int seats = 3;

    //int array for keeping track of the spots the player can bet on
    private boolean[] seatsPlayed = new boolean[seats];

    public Model() {
        cards = new ArrayList<>();
        playerHands = new ArrayList<>();
        dealerHand = new ArrayList<>();
    }


    @Override
    public void modelChanged() {

    }
}
