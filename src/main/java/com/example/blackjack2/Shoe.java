package com.example.blackjack2;

import java.util.ArrayList;

public class Shoe {

    //keeps track of the index of the top card
    private int topCardIndex;

    //Our shoe will be made out of 6 decks of cards
    private final int shoeDecks = 6;

    //The list of cards that will be our shoe
    private ArrayList<Card> cards;

    public Shoe() {
        topCardIndex = 0;
        cards = new ArrayList<Card>();
        //creates all of the cards that would be in the shoe given the number of decks used
        for (int i = 0; i < shoeDecks; i++) {
            for (Card.Rank rank : Card.Rank.values()) {
                for (Card.Suit suit : Card.Suit.values()) {
                    cards.add(new Card(rank, suit));
                }
            }
        }
    }

    //deals the top card in the shoe and moves the topCardIndex ahead one
    public Card drawCard() {
        int index = topCardIndex;
        topCardIndex++;
        return cards.get(index);
    }

    //shuffles the cards in the shoe
    public void shuffleShoe() {
        for (int i = 0; i < (shoeDecks * 52); i++) {
            int randomIndex = (int) (Math.random() * cards.size());
            Card temp = cards.get(0);
            cards.set(0, cards.get(randomIndex));
            cards.set(randomIndex, temp);
        }
    }
}
