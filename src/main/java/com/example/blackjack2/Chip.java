package com.example.blackjack2;

public class Chip {
    private int id;
    private int value;
    private int x, y, width, height;
    private Chip bottomChip;
    private Chip topChip;

    public Chip(int id, int value, int x, int y, Chip bottomChip) {
        this.id = id;
        this.value = value;
        this.width = 100;
        this.height = 60;
        this.bottomChip = bottomChip;
        this.topChip = null;

        if (bottomChip == null) {
            // If there's no bottom chip, place the chip at the provided coordinates
            this.x = x;
            this.y = y;
        } else {
            // If there's a bottom chip, place this chip above it with a slight offset
            this.x = bottomChip.getX();  // Align the x position with the bottom chip
            this.y = bottomChip.getY() - this.height / 4;  // Offset the y position by chip height + margin
            bottomChip.setTopChip(this);  // Bidirectional linking
        }
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

    public void setX(int x) {
        this.x = x;
        if (topChip != null) {
            topChip.setX(x);
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        if (topChip != null) {
            topChip.setY(y - (this.height / 4));  // Keep tight stack spacing
        }
    }

    public Chip getBottomChip() {
        return bottomChip;
    }

    public Chip getTopChip() {
        return topChip;
    }

    public void setBottomChip(Chip bottomChip) {
        this.bottomChip = bottomChip;
        if (bottomChip != null) {
            this.x = bottomChip.getX();
            this.y = bottomChip.getY() - (this.height / 4);  // Overlapping effect
            bottomChip.topChip = this;
        }
    }

    public void setTopChip(Chip topChip) {
        this.topChip = topChip;

        // Ensure topChip is not null before setting the bottom chip
        if (topChip != null) {
            topChip.setBottomChip(this);
        }
    }


    public void drag(int newX, int newY) {
        // Update chip position
        this.x = newX - width / 2;
        this.y = newY - height / 2;

        // Move the entire stack
        if (topChip != null) {
            topChip.drag(newX, this.y - (this.height / 4));  // Keep close spacing
        }
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + width &&
                    y >= this.y && y <= this.y + height;
    }
}
