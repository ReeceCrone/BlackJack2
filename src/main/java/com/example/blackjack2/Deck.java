package com.example.blackjack2;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    public Deck() {
        cards = new ArrayList<Card>();
        for (Card.Rank rank : Card.Rank.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }
    }
    public void shuffleDeck() {
        for (int i = 0; i < 52; i++) {
            int randomIndex = (int) (Math.random() * cards.size());
            Card temp = cards.get(0);
            cards.set(0, cards.get(randomIndex));
            cards.set(randomIndex, temp);
        }
    }

    public static void main(String[] args) {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        Deck deck3 = new Deck();

        //Test1 Test that all three decks are the same off the start
        for (int i = 0; i < 52; i++) {
            if (!deck1.cards.get(i).getRank().equals(deck2.cards.get(i).getRank()) &&
                    !deck1.cards.get(i).getSuit().equals(deck2.cards.get(i).getSuit()) &&
                    !deck1.cards.get(i).getRank().equals(deck3.cards.get(i).getRank()) &&
                    !deck1.cards.get(i).getSuit().equals(deck3.cards.get(i).getSuit())) {
                System.out.println("Test 1 failed, decks are not all the same at creation");
            }
        }

        //Test 2 Test if decks are all different after shuffling
        deck1.shuffleDeck();
        deck2.shuffleDeck();
        deck3.shuffleDeck();
        int counter = 0;
        for (int i = 0; i < 52; i++) {
            if (counter == 14) {
                System.out.println("Test 2 failed, it should be nearly impossible for three seperate" +
                        "decks of shuffled cards to have nearly a quarter of their cards in the same place" +
                        "as the other decks");
                break;
            }
            if (deck1.cards.get(i).getRank().equals(deck2.cards.get(i).getRank()) && deck1.cards.get(i).getSuit().equals(
                    deck2.cards.get(i).getSuit()) && deck1.cards.get(i).getRank().equals(deck3.cards.get(i).getRank()) &&
                    deck1.cards.get(i).getSuit().equals(deck3.cards.get(i).getSuit())) {
                    counter++;
            }
        }
    }
}
