package com.example.blackjack2;

public class Card {
    enum Suit {SPADES, CLUBS, DIAMONDS, HEARTS};
    enum Rank {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};

    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank.toString();
    }

    public String getSuit() {
        return suit.toString();
    }

    public int getValue() {
        switch (rank) {
            case ACE:
                return 1; // ACE could also be 11, depending on the game.
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
            case JACK:
            case QUEEN:
            case KING:
                return 10;
            default:
                throw new IllegalArgumentException("Unknown rank: " + rank);
        }
    }
}
