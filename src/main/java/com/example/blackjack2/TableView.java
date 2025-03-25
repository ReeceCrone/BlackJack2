package com.example.blackjack2;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class TableView extends BorderPane implements Subscriber {

    private Model model;
    private InteractionModel iModel;
    private Canvas myCanvas;
    private GraphicsContext gc;
    private ArrayList<Integer> chipsDrawn;
    private Button hitButton, stayButton, doubleButton, splitButton, dealButton,
            increaseBetButton, decreaseBetButton, resetButton;

    public TableView() {
        // Set up layout
        setPrefSize(1000, 600);

        // Create and configure canvas
        myCanvas = new Canvas(1000, 540);
        gc = myCanvas.getGraphicsContext2D();

        // Initialize buttons
        hitButton = new Button("Hit");
        stayButton = new Button("Stay");
        doubleButton = new Button("Double");
        splitButton = new Button("Split");
        dealButton = new Button("Deal");
        increaseBetButton = new Button("+");
        decreaseBetButton = new Button("-");
        resetButton = new Button("Reset");

        // Arrange buttons in an HBox
        HBox buttonBox1 = new HBox(10,splitButton, stayButton, hitButton, doubleButton, dealButton);
        buttonBox1.setAlignment(Pos.CENTER);

        HBox buttonBox2 = new HBox(10,decreaseBetButton, dealButton, increaseBetButton);
        buttonBox2.setAlignment(Pos.CENTER);

        VBox buttonBox = new VBox(10,buttonBox1, buttonBox2);
        buttonBox.setAlignment(Pos.CENTER);

        // Add elements to the layout
        setCenter(new StackPane(myCanvas));
        setBottom(buttonBox);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setInteractionModel(InteractionModel interactionModel) {
        this.iModel = interactionModel;
    }

    public void setupEvents(Controller appController) {
        setOnMousePressed(appController::handlePressed);
        setOnMouseDragged(appController::handleDragged);
        setOnMouseReleased(appController::handleReleased);
        setOnMouseMoved(appController::handleMoved);
    }

    public void draw() {
        chipsDrawn = new ArrayList<>();
        // Clear the canvas before redrawing
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        gc.setFill(Color.DARKOLIVEGREEN);
        gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        // Load chip image (from resources folder)
        Image chipImage = new Image(getClass().getResource("images/chip.png").toExternalForm());
        Image chipImage2 = new Image(getClass().getResource("images/chip2.png").toExternalForm());

        // Loop through each chip in the model's chip list
        for (Chip chip : model.getChips()) {
            // Start with the bottom chip and draw it
            Chip currentChip = chip;

            // Only draw if it's the bottom chip (no bottom chip)
            if (currentChip.getBottomChip() == null) {
                // Draw the chip at its current position
                if (iModel.getHoveredChip() == currentChip) {
                    gc.drawImage(chipImage2, currentChip.getX(), currentChip.getY(), currentChip.getWidth(), currentChip.getHeight());
                } else {
                    gc.drawImage(chipImage, currentChip.getX(), currentChip.getY(), currentChip.getWidth(), currentChip.getHeight());
                }
                chipsDrawn.add(currentChip.getId());

                // Move through the stack, drawing each chip
                while (currentChip.getTopChip() != null) {
                    currentChip = currentChip.getTopChip(); // Move to the next chip in the stack
                    if (!chipsDrawn.contains(currentChip.getId())) { // Prevent redrawing the same chip
                        if (iModel.getHoveredChip() == currentChip) {
                            gc.drawImage(chipImage2, currentChip.getX(), currentChip.getY(), currentChip.getWidth(), currentChip.getHeight());
                        } else {
                            gc.drawImage(chipImage, currentChip.getX(), currentChip.getY(), currentChip.getWidth(), currentChip.getHeight());
                        }
                        chipsDrawn.add(currentChip.getId());
                    }
                }
            }
        }
    }



    @Override
    public void modelChanged() {
        draw();  // Refresh the canvas when the model updates
    }
}


