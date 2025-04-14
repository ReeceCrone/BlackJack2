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
    private int playerTotal, dealerTotal;
    private int dealerCardCounter, playerCardCounter;
    private double randX, randY, finalX, finalY;
    private Card card;


    public Model() {
        stackables = new ArrayList<>();
        subs = new ArrayList<>();
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
        dealerCardCounter = 0;
        playerCardCounter = 0;
        dealerTotal = 0;
        playerTotal = 0;
        shoe = new Shoe();
        shoe.shuffleShoe();
        initializeChips();
    }

    public void addSubscriber(Subscriber s) {
        subs.add(s);
    }

    public List<Card> getPlayerCards() {
        return this.playerCards;
    }
    public List<Card> getDealerCards() {
        return this.dealerCards;
    }

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

    public List<Stackable> getStackables() {
        return stackables;
    }

    public void addStackable(Stackable s) {
        stackables.add(s);
    }

    public boolean isChipInStack(Chip chip) {
        return stackables.stream().anyMatch(s -> s instanceof ChipStack && ((ChipStack) s).onElement(chip.getX()
        , chip.getY()));
    }

    public void removeChipFromStack(Chip chip) {
        for (Stackable s : stackables) {
            if (s instanceof ChipStack && ((ChipStack) s).onElement(chip.getX(), chip.getY())) {
                ((ChipStack) s).removeChild(chip);
                break;
            }
        }
    }

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


    public void createNewStack(Chip chip) {
        ArrayList<Stackable> newStackables = new ArrayList<>();
        newStackables.add(chip);
        ChipStack newStack = new ChipStack(newStackables);
        stackables.add(newStack);
        notifySubscribers();
    }

    public Optional<Stackable> getNearestStackable(double x, double y, Stackable excluding) {
        return stackables.stream()
                .filter(s -> s != excluding && s.onElement(x, y))
                .findFirst();
    }


    public void mergeChips(Stackable movingChip, Stackable targetChip) {
        // Check if the movingChip is a ChipStack
        if (movingChip instanceof ChipStack) {
            // If the target is also a ChipStack, merge all chips from the moving stack into the target stack
            if (targetChip instanceof ChipStack) {
                List<Stackable> chips = movingChip.getChildren(); // Get all chips in the moving stack
                for (Stackable s : chips) {
                    // Add each chip to the target stack
                    ((ChipStack) targetChip).addChild((Chip) s);
                }
                // After merging, remove the moving stack from stackables
                stackables.remove(movingChip);
            } else if (targetChip instanceof Chip) {
                // Handle the case when the target is a single chip and we're merging a stack into it
                // Create a new ChipStack with the moving stack's chips + the target chip
                List<Stackable> newStackables = new ArrayList<>();
                List<Stackable> chips = movingChip.getChildren();
                newStackables.add((Chip) targetChip); // Add the target chip to the new stack
                newStackables.addAll(chips); // Add all chips from the moving stack

                System.out.println(newStackables.get(0).getY());
                ChipStack newStack = new ChipStack((ArrayList<Stackable>) newStackables);
                stackables.add(newStack); // Add the new stack to the model
                stackables.remove(movingChip); // Remove the old stack
                stackables.remove(targetChip); // Remove the target chip as it's now part of the new stack
            }
        }
        // If the movingChip is a single Chip
        else if (movingChip instanceof Chip) {
            // If the target is a ChipStack, add the moving chip to the stack
            if (targetChip instanceof ChipStack) {
                System.out.println("helloo");
                ((ChipStack) targetChip).addChild((Chip) movingChip);
                stackables.remove(movingChip); // Remove the chip from the stackables after adding it
            }
            // If the target is another Chip, merge the two chips into a new stack
            else if (targetChip instanceof Chip) {
                List<Stackable> newStackables = new ArrayList<>();
                newStackables.add((Chip) targetChip);
                newStackables.add((Chip) movingChip);
                ChipStack newStack = new ChipStack((ArrayList<Stackable>) newStackables);
                stackables.add(newStack); // Add the new stack to the model
                stackables.remove(movingChip); // Remove the moving chip
                stackables.remove(targetChip); // Remove the target chip
            }
        }
    }
    public void deal() {
        randX = Math.random() * 10;
        randY = Math.random() * 10;
        dealerCardCounter = 2;
        playerCardCounter = 2;
        playerCards.clear();
        dealerCards.clear();

        // Deal player card 1
        card = shoe.drawCard();
        finalX = 400 + randX;
        finalY = 350 + randY;
        card.setX(1200); // Start off-screen
        card.setY(-200); // Start off-screen
        playerCards.add(card);
        card.startMoveTo(finalX, finalY); // Start moving to final position
        notifySubscribers();



        // Delay for another 0.25 seconds before dealing the dealer card 1
        PauseTransition pause2 = new PauseTransition(Duration.seconds(0.25));
        pause2.setOnFinished(e -> {
            randX = Math.random() * 10;
            randY = Math.random() * 10;
            card = shoe.drawCard();
            finalX = 400 + randX;
            finalY = 150 + randY;
            card.setX(1200); // Start off-screen
            card.setY(-200); // Start off-screen
            dealerCards.add(card);
            card.startMoveTo(finalX, finalY); // Start moving to final position
            notifySubscribers();
        });

        pause2.play();

        // Delay for 0.25 seconds before dealing the next card
        PauseTransition pause1 = new PauseTransition(Duration.seconds(0.25));
        pause1.setOnFinished(e -> {
            // Deal player card 2
            randX = Math.random() * 10;
            randY = Math.random() * 10;
            card = shoe.drawCard();
            finalX = 440 + randX;
            finalY = 350 + randY;
            card.setX(1200); // Start off-screen
            card.setY(-200); // Start off-screen
            playerCards.add(card);
            card.startMoveTo(finalX, finalY); // Start moving to final position
            notifySubscribers();
        });
        pause1.setDelay(Duration.seconds(0.5)); // Delay this by 0.5 seconds to ensure the second player card is dealt first
        pause1.play();

        // Delay for another 0.25 seconds before dealing the dealer card 2
        PauseTransition pause3 = new PauseTransition(Duration.seconds(0.15));
        pause3.setOnFinished(e -> {
            randX = Math.random() * 10;
            randY = Math.random() * 10;
            card = shoe.drawCard();
            finalX = 440 + randX;
            finalY = 150 + randY;
            card.setX(1200); // Start off-screen
            card.setY(-200); // Start off-screen
            dealerCards.add(card);
            card.startMoveTo(finalX, finalY); // Start moving to final position
            notifySubscribers();
        });
        pause3.setDelay(Duration.seconds(0.75)); // Delay this by 0.75 seconds so it's after both dealer cards are set
        pause3.play();
    }


    public void hit() {
        if (getHandValue(playerCards) < 21) {
            double randX = Math.random() * 10;
            double randY = Math.random() * 10;
            Card card = shoe.drawCard();
            double finalX = (400 + playerCardCounter * 40 + randX);
            playerCardCounter++;
            double finalY = (350 + randY);
            card.setX(1200); // offscreen right
            card.setY(-200); // offscreen top
            card.startMoveTo(finalX, finalY); // move to end position
            playerCards.add(card);


            notifySubscribers();

            if (getHandValue(playerCards) > 21) {
                System.out.println("Player busts!");
            }


        }
    }

    public void stand() {
        // Reveal dealer's face-down card
        dealerCards.get(1).setFaceUp(true);
        notifySubscribers();

        drawNextDealerCard();
    }

    private void drawNextDealerCard() {
        int dealerTotal = getHandValue(dealerCards);

        if (dealerTotal < 17) {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(e -> {
                double randX = Math.random() * 10;
                double randY = Math.random() * 10;

                Card card = shoe.drawCard();
                double finalX = 400 + randX + dealerCardCounter * 40;
                double finalY = 150 + randY;
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

    private void evaluateGameResult() {
        int playerTotal = getHandValue(playerCards);
        int dealerTotal = getHandValue(dealerCards);

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



    public void notifySubscribers() {
        subs.forEach(Subscriber::modelChanged);
    }
}




