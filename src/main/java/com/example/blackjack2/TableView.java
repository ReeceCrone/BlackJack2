package com.example.blackjack2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.text.DecimalFormat;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.text.DecimalFormat;
import java.util.List;

public class TableView extends BorderPane implements Subscriber {
    private Model model;
    private InteractionModel iModel;
    private Canvas canvas;
    private GraphicsContext gc;

    private Image chipImage5, chipImage10, chipImage25, chipImageHovered;
    private Image cardBackImage;  // Image for the face-down card
    private final Image[] cardImages = new Image[52];  // Array to store card images (52 cards)

    private Button dealButton;
    private Button hitButton;
    private Button standButton;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public TableView() {
        setPrefSize(1200, 800);
        canvas = new Canvas(1200, 740);
        gc = canvas.getGraphicsContext2D();

        // Load chip images
        chipImage5 = new Image(getClass().getResource("images/chip.png").toExternalForm());
        chipImage10 = new Image(getClass().getResource("images/chip2.png").toExternalForm());
        chipImage25 = new Image(getClass().getResource("images/chip4.png").toExternalForm());
        chipImageHovered = new Image(getClass().getResource("images/chip3.png").toExternalForm());

        // Load card back image
        cardBackImage = new Image(getClass().getResource("images/cards/cardtemplate.png").toExternalForm());

        // Load card images for each card in the deck (adjust the paths accordingly)
        loadCardImages();

        dealButton = new Button("Deal");
        hitButton = new Button("Hit");
        standButton = new Button("Stand");

        HBox buttonBar = new HBox(20, dealButton, hitButton, standButton);
        buttonBar.setStyle("-fx-padding: 10; -fx-alignment: center;");
        setBottom(buttonBar);

        setCenter(canvas);
    }

    private void loadCardImages() {
        // Load the 52 card images (adjust paths accordingly)
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};

        int index = 0;
        for (String suit : suits) {
            for (String value : values) {
                String cardName = value + "_of_" + suit;
                cardImages[index++] = new Image(getClass().getResource("images/cards/" + cardName + ".png").toExternalForm());
            }
        }
    }

    public Button getDealButton() {
        return dealButton;
    }

    public Button getHitButton() {
        return hitButton;
    }

    public Button getStandButton() {
        return standButton;
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

        // Draw cards for the player
        drawCards(model.getPlayerCards(), 100, 400);  // Example: Player cards starting at position (100, 400)

        // Draw cards for the dealer
        drawCards(model.getDealerCards(), 100, 100);  // Example: Dealer cards starting at position (100, 100)

        // Draw chips
        for (Stackable stackable : model.getStackables()) {
            if (stackable instanceof ChipStack) {
                ChipStack stack = (ChipStack) stackable;
                drawChipStack(stack);
            } else if (stackable instanceof Chip) {
                drawChip((Chip) stackable);
            }
        }
    }

    private void drawCards(List<Card> cards, double startX, double startY) {
        double x = startX;
        double y = startY;
        for (Card card : cards) {
            Image cardImage = getCardImage(card);
            gc.drawImage(cardImage, x, y, 142, 192);  // Draw each card with width 71px and height 96px
            x += 80;  // Space between cards
        }
    }

    private Image getCardImage(Card card) {
        // For face-up cards, return the card's image
        if (card.isFaceUp()) {
            String cardName = card.toString();
            return new Image(getClass().getResource("images/cards/" + cardName + ".png").toExternalForm());
        } else {
            return cardBackImage;  // Face-down card
        }
    }

    private void drawChip(Chip chip) {
        Image imgToUse;
        switch ((int)(chip.getValue())) {
            case 5 -> {
                imgToUse = chipImage5;
            }
            case 10 -> {
                imgToUse = chipImage10;
            }
            case 25 -> {
                imgToUse = chipImage25;
            }
            default -> imgToUse = chipImage5;
        }
        if (iModel != null && iModel.getHoveredComponent() == chip) {
            imgToUse = chipImageHovered;
        }
        gc.drawImage(imgToUse, chip.getX(), chip.getY(), 80, 48);
        if (iModel.getHoveredComponent() == chip) {
            double textX = chip.getX() + 90;  // Center text horizontally
            double textY = chip.getY() + (24) + 5; // Adjust vertically
            gc.setFill(Color.BLACK);
            double amountInStacks = 0;
            ChipStack stack = model.getStackForChip(chip);
            if (stack != null) {
                for (int i = stack.getChildren().indexOf(chip); stack.getChildren().size() > i; i++) {
                    Stackable element = stack.getChildren().get(i);
                    if (element instanceof Chip) {
                        amountInStacks += ((Chip) element).getValue();
                    }
                }
            } else {
                amountInStacks = chip.getValue();
            }

            gc.setFont(new Font("Arial", 10));
            double[] xPoints = {textX - 10, textX - 2, textX - 2}; // X coordinates of the triangle
            double[] yPoints = {textY - 3, textY - 8, textY + 2}; // Y coordinates of the triangle
            gc.fillPolygon(xPoints, yPoints, 3);
            gc.fillText("$" + df.format(amountInStacks), textX, textY);
        }
    }

    private void drawChipStack(ChipStack stack) {
        for (Stackable chip : stack.getChildren()) {
            drawChip((Chip) chip);
        }
    }
}
