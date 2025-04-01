package com.example.blackjack2;

import java.util.List;

public class Chip implements Stackable {
    private double value, x, y;
    private int width = 100;
    private int height = 60;

    public Chip(int v, int x, int y) {
        this.value = v;
        this.x = x;
        this.y = y;
    }

    public double getValue() {
        return value;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    @Override
    public boolean isStack() {
        return false;
    }

    @Override
    public List<Stackable> getChildren() {
        return null;
    }

    @Override
    public void move(double dx, double dy) {
        x = dx;
        y = dy;
    }


    @Override
    public boolean onElement(double x, double y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }
}



