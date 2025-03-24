package com.example.blackjack2;

import javafx.scene.input.MouseEvent;

public class Controller {
    private Model model;
    private InteractionModel iModel;
    private ControllerState currentState;

    public Controller() {
        // Initialize to ready state
        currentState = readyState;
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

    // Define ControllerState as an abstract class that can be extended for different states
    private abstract class ControllerState {
        abstract void handlePressed(MouseEvent event);
        abstract void handleDragged(MouseEvent event);
        abstract void handleReleased(MouseEvent event);
    }

    // Ready state (when the game is idle and ready for interaction)
    ControllerState readyState = new ControllerState() {
        @Override
        void handlePressed(MouseEvent event) {
            // Check if a chip is clicked for dragging
            for (Chip chip : model.getChips()) {
                if (chip.contains((int) event.getX(), (int) event.getY())) {
                    iModel.setDraggedChip(chip);
                    currentState = draggingState; // Transition to dragging state
                    break;
                }
            }
        }

        @Override
        void handleDragged(MouseEvent event) {
            // In ready state, no dragging occurs
        }

        @Override
        void handleReleased(MouseEvent event) {
            // In ready state, nothing happens on release
        }
    };

    // Dragging state (when a chip is being dragged around)
    ControllerState draggingState = new ControllerState() {
        @Override
        void handlePressed(MouseEvent event) {
            // No action needed for pressed during dragging
        }

        @Override
        void handleDragged(MouseEvent event) {
            if (iModel.getDraggedChip() != null) {
                iModel.dragChip((int) event.getX(), (int) event.getY());
            }
        }

        @Override
        void handleReleased(MouseEvent event) {
            if (iModel.getDraggedChip() != null) {
                iModel.setDraggedChip(null);
                currentState = readyState; // Return to ready state after drag
            }
        }
    };

    // Reference to the chip currently being dragged
    private Chip draggedChip = null;
}
