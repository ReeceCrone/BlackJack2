package com.example.blackjack2;

import java.util.ArrayList;

public class Shoe {

    //Our shoe will be made out of 6 decks of cards
    private final int shoeDecks = 6;

    //The list of cards that will be our shoe
    private ArrayList<Card> shoe;

    public Shoe() {
        shoe = new ArrayList<Card>();
        for (int i = 0; i < shoeDecks; i++) {
            for (Card.Rank rank : Card.Rank.values()) {
                for (Card.Suit suit : Card.Suit.values()) {
                    shoe.add(new Card(rank, suit));
                }
            }
        }
    }

    public void shuffleShoe() {
        for (int i = 0; i < (shoeDecks * 52); i++) {
            int randomIndex = (int) (Math.random() * shoe.size());
            Card temp = shoe.get(0);
            shoe.set(0, shoe.get(randomIndex));
            shoe.set(randomIndex, temp);
        }
    }
}
