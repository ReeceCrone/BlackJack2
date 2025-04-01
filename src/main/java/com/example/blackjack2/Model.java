package com.example.blackjack2;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Stackable> stackables;
    private List<Subscriber> subscribers;

    public Model() {
        stackables = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public void addStackable(Stackable stackable) {
        stackables.add(stackable);
        notifySubscribers();
    }

    public void removeStackable(Stackable stackable) {
        stackables.remove(stackable);
        notifySubscribers();
    }

    public List<Stackable> getStackables() {
        return stackables;
    }

    public void moveStackable(Stackable stackable, double dx, double dy) {
        stackable.move(dx, dy);
        notifySubscribers();
    }

    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
    }

    private void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.modelChanged();
        }
    }
}



