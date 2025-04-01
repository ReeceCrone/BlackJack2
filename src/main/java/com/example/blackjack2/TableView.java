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
import java.util.HashSet;
import java.util.Set;

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
        chipsDrawn = new ArrayList<>();  // Reset before drawing

        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        gc.setFill(Color.DARKOLIVEGREEN);
        gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        Image chipImage = new Image(getClass().getResource("images/chip.png").toExternalForm());
        Image chipImage2 = new Image(getClass().getResource("images/chip2.png").toExternalForm());

        Set<Chip> drawnStacks = new HashSet<>();  // ✅ Prevent duplicate stacks

        for (Chip chip : model.getChips()) {
            if (chip.getBottomChip() == null && !drawnStacks.contains(chip)) {  // ✅ Only draw stack once
                Chip currentChip = chip;

                while (currentChip != null) {
                    Image imgToUse = (iModel.getHoveredChip() == currentChip) ? chipImage2 : chipImage;
                    gc.drawImage(imgToUse, currentChip.getX(), currentChip.getY(), currentChip.getWidth(), currentChip.getHeight());

                    chipsDrawn.add(currentChip.getId());
                    drawnStacks.add(currentChip);  // ✅ Mark chip as drawn
                    currentChip = currentChip.getTopChip();
                }
            }
        }
    }



    @Override
    public void modelChanged() {
        draw();  // Refresh the canvas when the model updates
    }
}


