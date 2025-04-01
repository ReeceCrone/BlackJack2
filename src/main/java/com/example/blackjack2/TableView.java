package com.example.blackjack2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TableView extends BorderPane implements Subscriber {
    private Model model;
    private InteractionModel iModel;
    private Canvas canvas;
    private GraphicsContext gc;
    private Image chipImage, chipImageHovered;

    public TableView() {
        setPrefSize(1000, 600);
        canvas = new Canvas(1000, 540);
        gc = canvas.getGraphicsContext2D();

        chipImage = new Image(getClass().getResource("images/chip.png").toExternalForm());
        chipImageHovered = new Image(getClass().getResource("images/chip2.png").toExternalForm());

        setCenter(canvas);
    }

    public void setModel(Model model) {
        this.model = model;
        model.addSubscriber(this);
    }

    public void setInteractionModel(InteractionModel iModel) {
        this.iModel = iModel;
        iModel.addSubscriber(this);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void modelChanged() {
        draw();
    }

    public void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.DARKOLIVEGREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Stackable stackable : model.getStackables()) {
            if (stackable instanceof ChipStack) {
                ChipStack stack = (ChipStack) stackable;
                drawChipStack(stack);
            } else if (stackable instanceof Chip) {
                drawChip((Chip) stackable);
            }
        }
    }

    private void drawChip(Chip chip) {
        Image imgToUse = (iModel != null && iModel.getHoveredComponent() == chip) ? chipImageHovered : chipImage;
        gc.drawImage(imgToUse, chip.getX(), chip.getY(), 100, 60);
        if (iModel.getHoveredComponent() == chip) {
            // Draw text (e.g., "$") centered relative to the chip
            double textX = chip.getX() + 105;  // Center text horizontally
            double textY = chip.getY() + (30) + 5; // Adjust vertically
            gc.setFill(Color.BLACK);
            double amountInStacks = 0;
            ChipStack stack = model.getStackForChip(chip);
            if (stack != null) {
                for (int i = 0; stack.getChildren().indexOf(chip) >= i; i++) {
                    Stackable element = stack.getChildren().get(i);
                    if (element instanceof Chip) {
                        amountInStacks += ((Chip) element).getValue();
                    }
                }
            } else {
                amountInStacks = chip.getValue();
            }

            gc.setFont(new Font("Arial", 20)); // Adjust the font size to 30 (you can make it larger or smaller as needed)
            gc.fillText("$" + amountInStacks, textX, textY);
        }
    }

    private void drawChipStack(ChipStack stack) {

        for (Stackable chip : stack.getChildren()) {

            drawChip((Chip) chip);
        }
    }
}

