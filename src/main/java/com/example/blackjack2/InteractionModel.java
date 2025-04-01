package com.example.blackjack2;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    private Stackable hoveredComponent;
    private Stackable selectedComponent;
    private List<Subscriber> subscribers;

    public InteractionModel() {
        subscribers = new ArrayList<>();
    }

    public void setHoveredComponent(Stackable component) {
        hoveredComponent = component;
        notifySubscribers();
    }

    public Stackable getHoveredComponent() {
        return hoveredComponent;
    }

    public void setSelectedComponent(Stackable component) {
        selectedComponent = component;
        notifySubscribers();
    }

    public Stackable getSelectedComponent() {
        return selectedComponent;
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

