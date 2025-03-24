package com.example.blackjack2;

import java.util.ArrayList;

public class InteractionModel {
    private ArrayList<Subscriber> subs;
    private Chip draggedChip;

    public InteractionModel() {
        subs = new ArrayList<>();
        draggedChip = null;
    }

    public void setDraggedChip(Chip draggedChip) {
        this.draggedChip = draggedChip;
    }

    public Chip getDraggedChip() {
        return draggedChip;
    }

    public void deselectChip() {
        draggedChip = null;
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
