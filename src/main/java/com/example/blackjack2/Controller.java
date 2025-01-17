package com.example.blackjack2;


import javafx.scene.input.MouseEvent;

public class Controller {
    private Model model;
    private InteractionModel iModel;
    private ControllerState currentState;

    public Controller() {
        currentState = ready;
    }

    public void setModel(Model m) {
        model = m;
    }

    public void setIModel(InteractionModel im) {
        iModel = im;
    }

    public void handlePressed(MouseEvent event) {
        currentState.handlePressed(event);
    }

    public void handleDragged(MouseEvent event) {
        currentState.handleDragged(event);
    }

    public void handleReleased(MouseEvent event) {
        currentState.handleReleased(event);
    }


    private abstract class ControllerState {
        void handlePressed(MouseEvent event) {}

        void handleDragged(MouseEvent event) {}

        void handleReleased(MouseEvent event) {}
    }

    ControllerState ready = new ControllerState() {

    };


}
