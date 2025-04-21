package com.example.blackjack2;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    private Chip hoveredComponent;
    private Stackable selectedComponent;
    //private Stackable bettedComponent = null;
    private List<Subscriber> subscribers;

    public InteractionModel() {
        subscribers = new ArrayList<>();
    }

    public void setHoveredComponent(Chip component) {
        hoveredComponent = component;
        notifySubscribers();
    }

    public Chip getHoveredComponent() {
        return hoveredComponent;
    }

//    public void setBettedComponent(Stackable component) {
//        bettedComponent = component;
//        bettedComponent.setX(580);
//        bettedComponent.setY(580);
//    }
//
//    public void clearBettedComponent() {
//        bettedComponent = null;
//    }
//
//    public Stackable getBettedComponent() {
//        return bettedComponent;
//    }

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

