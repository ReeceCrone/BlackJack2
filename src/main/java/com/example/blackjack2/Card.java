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
    public static void main(String[] args) {
        try {
            // Test 1: Check ACE card properties
            Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
            assert card1.getRank().equals("ACE");
            assert card1.getSuit().equals("HEARTS");
            assert card1.getValue() == 1;

            // Test 2: Check KING card properties
            Card card2 = new Card(Card.Rank.KING, Card.Suit.SPADES);
            assert card2.getRank().equals("KING");
            assert card2.getSuit().equals("SPADES");
            assert card2.getValue() == 10;

            // Test 3: Check FIVE card properties
            Card card3 = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS);
            assert card3.getRank().equals("FIVE");
            assert card3.getSuit().equals("DIAMONDS");
            assert card3.getValue() == 5;

            // Test 4: Check all ranks for correct values
            int[] expectedValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
            Card.Suit testSuit = Card.Suit.CLUBS;
            int i = 0;
            for (Card.Rank rank : Card.Rank.values()) {
                Card testCard = new Card(rank, testSuit);
                assert testCard.getValue() == expectedValues[i];
                i++;
            }

            System.out.println("All tests passed!");

        } catch (AssertionError e) {
            System.out.println("A test failed.");
        }
    }
}
