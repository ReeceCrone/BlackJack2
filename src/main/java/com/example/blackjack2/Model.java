package com.example.blackjack2;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Model {
    private List<Stackable> stackables;
    private List<Card> playerCards;
    private List<Card> dealerCards;
    private Shoe shoe;
    private ArrayList<Subscriber> subs;
    private int dealerCardCounter, playerCardCounter, playerTotal, dealerTotal;
    private BettingPosition bettingPosition;
    private boolean gameOver;


    public Model() {
        stackables = new ArrayList<>();
        subs = new ArrayList<>();
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
        dealerCardCounter = 0;
        playerCardCounter = 0;
        playerTotal = 0;
        dealerTotal = 0;
        bettingPosition = new BettingPosition(600, 600, 120, 72, 1);
        gameOver = true;
        shoe = new Shoe();
        shoe.shuffleShoe();
        initializeChips();
    }


    public void addSubscriber(Subscriber s) {
        subs.add(s);
    }

    public BettingPosition getBettingPosition() {
        return bettingPosition;
    }

    public boolean isGameOver() {
        return gameOver;
    }


    public List<Card> getPlayerCards() {
        return this.playerCards;
    }

    public List<Card> getDealerCards() {
        return this.dealerCards;
    }


    /**
     * creates some chips for the start of the game
     */
    private void initializeChips() {
        ArrayList<Stackable> newStackables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Chip chip = new Chip(5, 250, 250 - (i * 5)); // Offset Y
            newStackables.add(chip);
        }
        Chip chip = new Chip(5, 450, 250);
        Chip chip2 = new Chip(10, 550, 250);
        Chip chip3 = new Chip(25, 250, 400);
        ChipStack initialStack = new ChipStack(newStackables);
        stackables.add(initialStack);
        stackables.add(chip);
        stackables.add(chip2);
        stackables.add(chip3);
    }



    /**
     * @return the list of all stackable objects (chips, chip stacks)
     */
    public List<Stackable> getStackables() {
        return stackables;
    }



    /**
     * Adds a stackable object (Chip or ChipStack) to the model.
     *
     * @param s the stackable object to add
     */
    public void addStackable(Stackable s) {
        stackables.add(s);
    }


    /**
     * Finds the chip stack that contains the given chip.
     *
     * @param chip the chip to search for
     * @return the containing ChipStack or null if not found
     */
    public ChipStack getStackForChip(Chip chip) {
        for (Stackable s : stackables) {
            if (s instanceof ChipStack) {
                ChipStack stack = (ChipStack) s;
                if (stack.getChildren().contains(chip)) {
                    return stack;
                }
            }
        }
        return null; // Chip is not in any stack
    }

    /**
     * Finds the first stackable object near the given position, excluding the provided object.
     *
     * @param x         x coordinate
     * @param y         y coordinate
     * @param excluding the stackable to exclude from the check
     * @return an Optional of a nearby Stackable
     */
    public Optional<Stackable> getNearestStackable(double x, double y, Stackable excluding) {
        return stackables.stream()
                .filter(s -> s != excluding && s.onElement(x, y))
                .findFirst();
    }

    /**
     * Merges two stackable objects: chip into chip, chip into stack, stack into stack, etc.
     *
     * @param moving the stackable being moved
     * @param target the stackable being merged into
     */
    public void mergeChips(Stackable moving, Stackable target) {
        // Check if the moving is a ChipStack
        if (moving instanceof ChipStack) {
            // If the target is also a ChipStack, merge all chips from the moving stack into the target stack
            if (target instanceof ChipStack) {
                List<Stackable> chips = moving.getChildren(); // Get all chips in the moving stack
                for (Stackable s : chips) {
                    // Add each chip to the target stack
                    ((ChipStack) target).addChild((Chip) s);
                }
                // After merging, remove the moving stack from stackables
                stackables.remove(moving);
            } else if (target instanceof Chip) {
                // Handle the case when the target is a single chip and we're merging a stack into it
                // Create a new ChipStack with the moving stack's chips + the target chip
                List<Stackable> newStackables = new ArrayList<>();
                List<Stackable> chips = moving.getChildren();
                newStackables.add((Chip) target); // Add the target chip to the new stack
                newStackables.addAll(chips); // Add all chips from the moving stack

                System.out.println(newStackables.get(0).getY());
                ChipStack newStack = new ChipStack((ArrayList<Stackable>) newStackables);
                stackables.add(newStack); // Add the new stack to the model
                stackables.remove(moving); // Remove the old stack
                stackables.remove(target); // Remove the target chip as it's now part of the new stack
            }
        }
        // If the moving is a single Chip
        else if (moving instanceof Chip) {
            // If the target is a ChipStack, add the moving chip to the stack
            if (target instanceof ChipStack) {
                ((ChipStack) target).addChild((Chip) moving);
                stackables.remove(moving); // Remove the chip from the stackables after adding it
            }
            // If the target is another Chip, merge the two chips into a new stack
            else if (target instanceof Chip) {
                List<Stackable> newStackables = new ArrayList<>();
                newStackables.add((Chip) target);
                newStackables.add((Chip) moving);
                ChipStack newStack = new ChipStack((ArrayList<Stackable>) newStackables);
                stackables.add(newStack); // Add the new stack to the model
                stackables.remove(moving); // Remove the moving chip
                stackables.remove(target); // Remove the target chip
            }
        }
    }

    /**
     * Starts a new round by resetting game state and dealing initial cards.
     */
    public void deal() {
        resetGameState();

        dealPlayerCard(0, 0.0);
        dealDealerCard(1, 0.25);
        dealPlayerCard(1, 0.5);
        dealDealerCard(2, 0.75);
        System.out.println(getHandValue(playerCards));

    }

    /**
     * Resets for a new deal
     */
    private void resetGameState() {
        gameOver = false;
        dealerCardCounter = 2;
        playerCardCounter = 2;
        playerTotal = 0;
        dealerTotal = 0;
        playerCards.clear();
        dealerCards.clear();
    }



    /**
     * Deals a card to the player with animation delay.
     *
     * @param index        position index for card placement
     * @param delaySeconds delay before card is dealt
     */
    private void dealPlayerCard(int index, double delaySeconds) {
        PauseTransition pause = new PauseTransition(Duration.seconds(delaySeconds));
        pause.setOnFinished(e -> {
            Card card = shoe.drawCard();
            double randX = Math.random() * 10;
            double randY = Math.random() * 10;
            double finalX = 400 + index * 40 + randX;
            double finalY = 250 + randY;

            //card starts offscreen
            card.setX(1200);
            card.setY(-200);
            playerCards.add(card);
            //card moves to final position
            card.startMoveTo(finalX, finalY);

            playerTotal = getHandValue(playerCards);
            notifySubscribers();

            if (playerCardCounter == 2 && playerTotal == 21) {
                System.out.println("Blackjack!");
                stand();
            }
        });
        pause.play();
    }

    /**
     * Deals a card to the dealer with animation delay.
     *
     * @param index        position index for card placement
     * @param delaySeconds delay before card is dealt
     */
    private void dealDealerCard(int index, double delaySeconds) {
        PauseTransition pause = new PauseTransition(Duration.seconds(delaySeconds));
        pause.setOnFinished(e -> {
            Card card = shoe.drawCard();
            double randX = Math.random() * 10;
            double randY = Math.random() * 10;
            double finalX = 400 + (index - 1) * 40 + randX; // index-1 aligns with player layout
            double finalY = 30 + randY;

            //card starts offscreen
            card.setX(1200);
            card.setY(-200);
            dealerCards.add(card);
            // card moves to final position
            card.startMoveTo(finalX, finalY);
            dealerTotal = getHandValue(dealerCards);
            notifySubscribers();
        });
        pause.play();
    }

    /**
     * Adds one more card to the player's hand and checks for bust or auto-stand.
     */
    public void hit() {
        if (getHandValue(playerCards) < 21) {
            double randX = Math.random() * 10;
            double randY = Math.random() * 10;
            Card card = shoe.drawCard();
            double finalX = (400 + playerCardCounter * 40 + randX);
            playerCardCounter++;
            double finalY = (250 + randY);
            card.setX(1200); // offscreen right
            card.setY(-200); // offscreen top
            card.startMoveTo(finalX, finalY); // move to end position
            playerCards.add(card);
            playerTotal = getHandValue(playerCards);


            notifySubscribers();

            if (playerTotal > 21) {
                System.out.println("Player busts!");
                gameOver = true;
            }
            if (playerTotal == 21) {
                stand();
                gameOver = true;
            }


        }
    }


    /**
     * Ends the player's turn and begins the dealer's turn.
     * Automatically flips the dealer's hidden card.
     */
    public void stand() {
        // Reveal dealer's face-down card
        //dealerCards.get(1).setFaceUp(true);
        notifySubscribers();

        drawNextDealerCard();
    }


    /**
     * Recursively draws dealer cards until dealer has 17 or more.
     */
    private void drawNextDealerCard() {
        dealerTotal = getHandValue(dealerCards);

        if (dealerTotal < 17) {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(e -> {
                double randX = Math.random() * 10;
                double randY = Math.random() * 10;

                Card card = shoe.drawCard();
                double finalX = 400 + randX + dealerCardCounter * 40;
                double finalY = 30 + randY;
                card.setX(1200);
                card.setY(-200);
                dealerCardCounter++;
                dealerCards.add(card);
                card.startMoveTo(finalX, finalY);
                notifySubscribers();

                // After this card is drawn, check again
                drawNextDealerCard();
            });
            pause.setDelay(Duration.seconds(0.25));
            pause.play();
        } else {
            evaluateGameResult(); // Once dealer stands, evaluate result
        }
    }

    /**
     * Evaluates and prints the result of the game (win/lose/push).
     */
    private void evaluateGameResult() {
        gameOver = true;
        System.out.println("Player: " + playerTotal + ", Dealer: " + dealerTotal);

        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("Player wins!");
        } else if (playerTotal < dealerTotal) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("Push (tie)");
        }

        notifySubscribers();
    }


    /**
     * Calculates the Blackjack hand value for the given hand.
     * Handles ace as 1 or 11 appropriately.
     *
     * @param hand list of cards
     * @return total hand value
     */
    private int getHandValue(List<Card> hand) {
        int value = 0;
        int aces = 0;

        for (Card card : hand) {
            value += card.getValue(); // Should be 11 for Ace, 10 for face cards, else rank value
            if (card.isAce()) {
                aces++;
            }
        }

        while (value > 21 && aces > 0) {
            value -= 10; // Ace now counts as 1 instead of 11
            aces--;
        }

        return value;
    }


    /**
     * Notifies all subscribed views that the model has changed.
     */
    public void notifySubscribers() {
        subs.forEach(Subscriber::modelChanged);
    }
}