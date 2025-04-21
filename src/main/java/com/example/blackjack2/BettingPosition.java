package com.example.blackjack2;

import java.util.ArrayList;

public class BettingPosition {

    private double x, y;
    private double width, height;
    private Stackable Contents;
    private int id;
    private boolean empty;


    public BettingPosition(double x, double y, double width, double height, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.empty = true;
    }

    public double getY() {
        return y;
    }
    public double getX() {
        return x;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public void setContents(Stackable Contents) {
        this.Contents = Contents;
    }
    public Stackable getContents() {
        return Contents;
    }
    public int getId() {
        return id;
    }
    public boolean isEmpty() {
        return empty;
    }
    public boolean isInside(double x, double y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }
}
