package com.example.blackjack2;

public class Card {
    public enum Suit {SPADES, CLUBS, DIAMONDS, HEARTS};
    public enum Rank {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};

    private Rank rank;
    private Suit suit;
    private int value;
    private double x, y;
    private double width = 80;
    private double height = 120;
    private boolean faceUp;
    private double targetX, targetY; // destination
    private boolean moving = false;
    private double angle = 45;     // Start at 45°
    private boolean flipping = true;
    private double flipProgress = 0.0; // 0.0 = back, 1.0 = face
    private double endAngle;

    public Card(Rank rank, Suit suit, double x, double y) {
        this.rank = rank;
        this.suit = suit;
        this.x = x;
        this.y = y;
        this.faceUp = true;
        this.endAngle = Math.random();
        if (endAngle < 0.5) {
            endAngle = endAngle * -5;
        } else {
            endAngle = endAngle * 5;
        }
    }

    /**
     * Initiates a smooth movement of the object to a target position (tx, ty).
     * Also starts a flip animation by resetting flip progress and angle.
     *
     * @param tx the target x-coordinate
     * @param ty the target y-coordinate
     */
    public void startMoveTo(double tx, double ty) {
        this.targetX = tx;
        this.targetY = ty;
        this.moving = true;
        this.angle = 45;
        this.flipProgress = 0.0;
        this.flipping = true;
    }

    /**
     * Updates the current position and angle of the object as it moves toward its target.
     * Also progresses the flip animation. Once the movement is nearly complete, it finalizes
     * the position and stops further animation.
     */
    public void updatePosition() {
        double dx = targetX - x;
        double dy = targetY - y;

        if (Math.abs(dx) < 1 && Math.abs(dy) < 1) {
            x = targetX;
            y = targetY;
            angle = endAngle;
            flipProgress = 1.0;
            moving = false;
            flipping = false;
        } else {
            x += dx * 0.15;
            y += dy * 0.15;
            angle += (endAngle - angle) * 0.15;
            // ease to 0
            if (flipping) {
                flipProgress += 0.15 * (1 - flipProgress);
                if (flipProgress >= 1.0) {
                    flipProgress = 1.0;
                    flipping = false;
                }
            }
        }
    }
    public boolean isMoving() { return moving; }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public double getAngle() { return angle; }

    public double getFlipProgress() { return flipProgress; }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getWidth() { return width; }

    public double getHeight() { return height; }

    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }

    public boolean isAce() {
        if (rank == Rank.ACE) return true;
        return false;
    }

    public String getRank() {
        return rank.toString();
    }

    public String getSuit() {
        return suit.toString();
    }

    /**
     * gets cards value
     * @return an int of th cards value
     */
    public int getValue() {
        switch (rank) {
            case ACE:
                return 11; // ACE could also be 11, depending on the game.
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


    //this is for later, incase I want to be able to move the cards
    public boolean onElement(double x, double y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }

    /**
     * returns the cards string format so we can get its image
     * @return a string respresenting the card
     */
    public String toString() {
        String card_rank;
        String card_suit;
        switch (rank) {
            case ACE:
                card_rank = "ace";
                break;
            case TWO:
                card_rank = "2";
                break;
            case THREE:
                card_rank = "3";
                break;
            case FOUR:
                card_rank = "4";
                break;
            case FIVE:
                card_rank = "5";
                break;
            case SIX:
                card_rank = "6";
                break;
            case SEVEN:
                card_rank = "7";
                break;
            case EIGHT:
                card_rank = "8";
                break;
            case NINE:
                card_rank = "9";
                break;
            case TEN:
                card_rank = "10";
                break;
            case JACK:
                card_rank = "jack";
                break;
            case QUEEN:
                card_rank = "queen";
                break;
            case KING:
                card_rank = "king";
                break;
            default:
                throw new IllegalArgumentException("Unknown rank: " + rank);
        }
        switch (suit) {
            case SPADES:
                card_suit = "spades";
                break;
            case CLUBS:
                card_suit = "clubs";
                break;
            case DIAMONDS:
                card_suit = "diamonds";
                break;
            case HEARTS:
                card_suit = "hearts";
                break;
            default:
                throw new IllegalArgumentException("Unknown suit: " + suit);
        }
        return card_rank + "_of_" + card_suit;
    }




    public static void main(String[] args) {
        try {
            // Test 1: Check ACE card properties
            Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS, 0, 0);
            assert card1.getRank().equals("ACE");
            assert card1.getSuit().equals("HEARTS");
            assert card1.getValue() == 1;

            // Test 2: Check KING card properties
            Card card2 = new Card(Card.Rank.KING, Card.Suit.SPADES, 0, 0);
            assert card2.getRank().equals("KING");
            assert card2.getSuit().equals("SPADES");
            assert card2.getValue() == 10;

            // Test 3: Check FIVE card properties
            Card card3 = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS, 0, 0);
            assert card3.getRank().equals("FIVE");
            assert card3.getSuit().equals("DIAMONDS");
            assert card3.getValue() == 5;

            // Test 4: Check all ranks for correct values
            int[] expectedValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
            Card.Suit testSuit = Card.Suit.CLUBS;
            int i = 0;
            for (Card.Rank rank : Card.Rank.values()) {
                Card testCard = new Card(rank, testSuit, 0, 0);
                assert testCard.getValue() == expectedValues[i];
                i++;
            }

            System.out.println("All tests passed!");

        } catch (AssertionError e) {
            System.out.println("A test failed.");
        }
    }
}
