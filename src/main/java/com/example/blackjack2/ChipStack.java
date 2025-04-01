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
            addChild(child);
        }
    }

    public void addChild(Stackable child) {
        children.add(child);
        int stackHeight = children.size();

        // Adjust the Y-coordinate to stack chips vertically.
        // Instead of moving chips downward (negative offset), we move them upward.
        child.setY(y - (stackHeight * 15) + 15);  // Stack chips with a 15 unit offset
        child.setX(x);
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
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
        return children; // Return a copy to prevent modification.
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