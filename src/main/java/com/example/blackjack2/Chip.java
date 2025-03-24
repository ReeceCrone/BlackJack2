package com.example.blackjack2;

public class Chip {
    private int id;
    private int value;
    private int x, y, width, height;

    public Chip(int id, int value, int x, int y) {
        this.id = id;
        this.value = value;
        this.x = x;
        this.y = y;
        this.width = 60;
        this.height = 30;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }
    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void drag(int newX, int newY) {
        this.x = newX - width / 2;  // Center the chip on the mouse pointer
        this.y = newY - height / 2;
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + width &&
                y >= this.y && y <= this.y + height;
    }
}
