package com.example.blackjack2;

import java.util.ArrayList;
import java.util.List;

public class ChipStack implements Stackable {
    private List<Stackable> children;
    private double x;
    private double y;

    public ChipStack(ArrayList<Stackable> children) {
        this.x = children.get(0).getX();
        this.y = children.get(0).getY();
        this.children = new ArrayList<>();
        for (Stackable child : children) {
            this.children.add(child);
        }
    }

    public void addChild(Stackable child) {
        children.add(child);
    }

    public void removeChild(Stackable child) {
        children.remove(child);
    }

    @Override
    public boolean isStack() {
        return true;
    }

    @Override
    public List<Stackable> getChildren() {
        return new ArrayList<>(children); // Return a copy to prevent modification.
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void move(double dx, double dy) {
        for (Stackable child : children) {
            child.move(dx, dy);
        }
    }

    @Override
    public boolean onElement(double x, double y) {
        for (Stackable child : children) {
            if (child.onElement(x, y)) {
                return true;
            }
        }
        return false;

    }


}