package com.example.blackjack2;

import java.util.ArrayList;

public class Model{
    private ArrayList<Subscriber> subs;
    private Shoe shoe;
    private ArrayList<ArrayList<Card>> playerHands;
    private ArrayList<Card> dealerHand;

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
    }

    // add a subscriber to the list of subscribers
    public void addSubscriber(Subscriber subscriber) {
        subs.add(subscriber);
    }

    //have all subscribers run their modelChanged method
    public void notifySubscribers() {
        subs.forEach(Subscriber::modelChanged);
    }

}
