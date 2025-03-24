package com.example.blackjack2;

import java.util.ArrayList;

public class Model{
    private ArrayList<Subscriber> subs;
    private Shoe shoe;
    private ArrayList<ArrayList<Card>> playerHands;
    private ArrayList<Card> dealerHand;
    private ArrayList<Chip> chips;
    private int next_chip_id, bankroll;


    //the number of spots that can be played at the blackjack table
    private final int seats = 3;

    //int array for keeping track of the spots the player can bet on
    private boolean[] seatsPlayed = new boolean[seats];

    public Model() {
        subs = new ArrayList<>();
        Shoe shoe = new Shoe();
        shoe.shuffleShoe();
        playerHands = new ArrayList<>();
        dealerHand = new ArrayList<>();
        chips = new ArrayList<>();
        next_chip_id = 0;
        bankroll = 50;
        for (int i = 0; i < 10; i++) {
            Chip chip = new Chip(next_chip_id, 5, 250, 300);
            chips.add(chip);
            next_chip_id++;
        }
    }

    public ArrayList<Chip> getChips() {
        return chips;
    }

    // add a subscriber to the list of subscribers
    public void addSubscriber(Subscriber subscriber) {
        subs.add(subscriber);
    }

    //have all subscribers run their modelChanged method
    public void notifySubscribers() {
        subs.forEach(Subscriber::modelChanged);
    }

    public void hit (int seat) {

    }

}
