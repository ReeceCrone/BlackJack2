package com.example.blackjack2;

import java.util.ArrayList;

public class InteractionModel {
    private ArrayList<Subscriber> subs;

    public InteractionModel() {
        subs = new ArrayList<>();
    }

    public void addSubscriber(Subscriber s) {
        subs.add(s);
    }

    public void notifySubscribers() {
        subs.forEach(Subscriber::modelChanged);
    }

}
