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

    public void handleMoved(MouseEvent event) {
        currentState.handleMoved(event);
    }

    // Define ControllerState as an abstract class that can be extended for different states
    private abstract class ControllerState {
        abstract void handlePressed(MouseEvent event);
        abstract void handleDragged(MouseEvent event);
        abstract void handleReleased(MouseEvent event);
        abstract void handleMoved(MouseEvent event);
    }

    // Ready state (when the game is idle and ready for interaction)
    ControllerState readyState = new ControllerState() {
        @Override
        void handlePressed(MouseEvent event) {
            // Check if a chip is clicked for dragging
            Chip selectedChip = null;
            for (Chip chip : model.getChips()) {
                if (chip.contains((int) event.getX(), (int) event.getY())) {
                    selectedChip = chip;
                }
            }

            // If a chip is selected, prepare for dragging
            if (selectedChip != null) {
                iModel.setDraggedChip(selectedChip);
                System.out.println("Selected chip: " + selectedChip);

                // Unstack from previous position
                if (selectedChip.getBottomChip() != null) {
                    Chip bottomChip = selectedChip.getBottomChip();
                    if (bottomChip != null) {  // Ensure it's not null before calling setTopChip
                        bottomChip.setTopChip(null);
                    }
                    selectedChip.setBottomChip(null);
                }


                // Transition to dragging state
                currentState = draggingState;
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

        @Override
        void handleMoved(MouseEvent event) {
            Chip hoveredChip = null;

            for (Chip chip : model.getChips()) {
                if (chip.contains((int) event.getX(), (int) event.getY())) {
                    hoveredChip = chip;
                }
            }

            // Update the interaction model if the hovered chip changes
            if (iModel.getHoveredChip() != hoveredChip) {
                iModel.setHoveredChip(hoveredChip);
                model.notifySubscribers();  // Redraw the view to update highlights
            }
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
                Chip draggedChip = iModel.getDraggedChip();
                Chip targetChip = null;

                for (Chip chip : model.getChips()) {
                    if (chip != draggedChip && chip.contains((int) event.getX(), (int) event.getY()) && chip.getTopChip() == null) {
                        targetChip = chip;
                        break;
                    }
                }

                if (targetChip != null) {
                    // Set the new stacking relationship
                    targetChip.setTopChip(draggedChip);
                }

                iModel.setDraggedChip(null);
                model.notifySubscribers();
                currentState = readyState; // Return to ready state
            }
        }

        @Override
        void handleMoved(MouseEvent event) {
            //No action needed
        }
    };

}
