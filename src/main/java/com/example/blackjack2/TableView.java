package com.example.blackjack2;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class TableView extends StackPane implements Subscriber {

    private GraphicsContext gc;
    private Model model;
    private InteractionModel interactionModel;

    public TableView() {
        Canvas myCanvas = new Canvas(500, 800);
        gc = myCanvas.getGraphicsContext2D();
        myCanvas.setFocusTraversable(true);
        myCanvas.requestFocus();
        this.getChildren().add(myCanvas);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setInteractionModel(InteractionModel interactionModel) {
        this.interactionModel = interactionModel;
    }

    public void setupEvents(Controller appController) {
        setOnMousePressed(appController::handlePressed);
        setOnMouseDragged(appController::handleDragged);
        setOnMouseReleased(appController::handleReleased);
        setOnKeyPressed(appController::handleKeyTyped);
        setOnKeyReleased(appController::handleKeyReleased);
    }


    public void draw() {
        gc.clearRect(0, 0, 500, 800);

    }

    @Override
    public void modelChanged() {

    }
}
