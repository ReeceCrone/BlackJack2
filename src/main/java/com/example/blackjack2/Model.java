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
        for (int i = 0; i < 1; i++) {
            Chip chip = new Chip(5, 250, 250);
            newStackables.add(chip);
        }
        ChipStack initialStack = new ChipStack(newStackables);
        stackables.add(initialStack);
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

    public void createNewStack(Stackable chip) {
        ArrayList<Stackable> newStackables = new ArrayList<>();
        newStackables.add(chip);
        ChipStack newStack = new ChipStack(newStackables);
        stackables.add(newStack);
    }

    public Optional<Stackable> getNearestStackable(double x, double y, Stackable excluding) {
        return stackables.stream()
                .filter(s -> s != excluding && s.onElement(x, y))
                .findFirst();
    }

    public void mergeChips(Stackable movingChip, Chip targetChip) {
        for (Stackable s : stackables) {
            if (s instanceof ChipStack && ((ChipStack) s).onElement(targetChip.getX(), targetChip.getY())) {
                ((ChipStack) s).addChild(movingChip);
                return;
            }
        }
        ArrayList<Stackable> newStack = new ArrayList<>();
        newStack.add(targetChip);
        newStack.add(movingChip);
        ChipStack newStack1 = new ChipStack(newStack);

        stackables.add(newStack1);
    }

    public void notifySubscribers() {
        subs.forEach(Subscriber::modelChanged);
    }
}




