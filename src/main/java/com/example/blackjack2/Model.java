package com.example.blackjack2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Model {
    private List<Stackable> stackables;
    private ArrayList<Subscriber> subs;

    public Model() {
        stackables = new ArrayList<>();
        subs = new ArrayList<>();
        initializeChips();
    }

    public void addSubscriber(Subscriber s) {
        subs.add(s);
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



    public void notifySubscribers() {
        subs.forEach(Subscriber::modelChanged);
    }
}




