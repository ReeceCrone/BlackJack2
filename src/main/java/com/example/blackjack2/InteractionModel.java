package com.example.blackjack2;

import java.util.ArrayList;

public class InteractionModel {
    private ArrayList<Subscriber> subs;
    private Chip draggedChip;
    private Chip hoveredChip;

    public InteractionModel() {
        subs = new ArrayList<>();
        draggedChip = null;
        hoveredChip = null;
    }

    public void setDraggedChip(Chip draggedChip) {
        this.draggedChip = draggedChip;
        notifySubscribers();
    }

    public void setHoveredChip(Chip hoveredChip) {
        this.hoveredChip = hoveredChip;
        notifySubscribers();
    }

    public Chip getDraggedChip() {
        return draggedChip;
    }

    public Chip getHoveredChip() {
        return hoveredChip;
    }


    public void dragChip(int dX, int dY) {
        if (draggedChip != null) {
            draggedChip.drag(dX, dY);
            notifySubscribers();
        }
    }


    public void addSubscriber(Subscriber s) {
        subs.add(s);
    }

    public void notifySubscribers() {
        subs.forEach(Subscriber::modelChanged);
    }

}
