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
            addChild(child);  // Properly set the initial Y-position of each chip
        }
    }

    public void addChild(Stackable child) {
        children.add(child);
        int stackHeight = children.size();
        child.setY(this.y + (- 10 * stackHeight)); // Adjust Y relative to stack's position
        child.setX(this.x);  // Ensure X is aligned with the stack
    }

    @Override
    public void setX(double x) {
        this.x = x;
        // Update the X position of all child chips
        for (Stackable child : children) {
            child.setX(x);
        }
    }

    @Override
    public void setY(double y) {
        this.y = y;
        // Update the Y position of all child chips
        for (Stackable child : children) {
            child.setY(y + (- 10 * children.indexOf(child)));
        }
    }

    public void removeChild(Chip child) {
        children.remove(child);
    }

    @Override
    public boolean isStack() {
        return true;
    }

    @Override
    public List<Stackable> getChildren() {
        return children;
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
        // Move all children relative to the stack's new position
        this.x += dx;
        this.y += dy;
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
