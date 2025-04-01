package com.example.blackjack2;

import java.util.List;

public interface Stackable {
    double x = 0;
    double y = 0;
    boolean isStack();
    List<Stackable> getChildren();
    public double getX();
    public double getY();
    public void move(double x, double y);
    public boolean onElement(double x, double y);
}
