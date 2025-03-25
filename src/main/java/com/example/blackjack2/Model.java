package com.example.blackjack2;

import java.util.ArrayList;

public class Model {
    private ArrayList<Subscriber> subs;
    private Shoe shoe;
    private ArrayList<ArrayList<Card>> playerHands;
    private ArrayList<Card> dealerHand;
    private ArrayList<Chip> chips;
    private int next_chip_id, bankroll;

    // The number of spots that can be played at the blackjack table
    private final int seats = 3;

    // Int array for keeping track of the spots the player can bet on
    private boolean[] seatsPlayed = new boolean[seats];

    public Model() {
        subs = new ArrayList<>();
        shoe = new Shoe();  // Initialize the shoe correctly
        shoe.shuffleShoe();
        playerHands = new ArrayList<>();
        dealerHand = new ArrayList<>();
        chips = new ArrayList<>();
        next_chip_id = 0;
        bankroll = 50;

        // Initialize chip stacking logic
        Chip bottomChip = null;
        Chip prevChip = null;
        int chipYPosition = 300;  // Starting Y position for the first chip

        // Create a stack of 10 chips
        for (int i = 0; i < 10; i++) {
            // Each chip's Y position will be offset to create a stack effect
            Chip chip = new Chip(next_chip_id, 5, 250, 250, bottomChip);
            if (prevChip != null) {
                prevChip.setTopChip(chip);
            }

            chips.add(chip);

            // Update for next chip's position (vertical stack)
            bottomChip = chip;
            prevChip = chip;
            chipYPosition += 5;  // Increase Y to stack the next chip slightly above the previous one
            next_chip_id++;
        }
    }

    public ArrayList<Chip> getChips() {
        return chips;
    }

    // Add a subscriber to the list of subscribers
    public void addSubscriber(Subscriber subscriber) {
        subs.add(subscriber);
    }

    // Have all subscribers run their modelChanged method
    public void notifySubscribers() {
        subs.forEach(Subscriber::modelChanged);
    }

    public void hit(int seat) {
        // Implement hit logic here
    }
}

