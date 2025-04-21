package com.example.blackjack2;

import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Optional;

public class Controller {
    private enum State { READY, DRAGGING }
    private State currentState;

    private Model model;
    private InteractionModel iModel;
    private TableView view;
    private double lastX, lastY;

    public Controller(Model model, InteractionModel iModel, TableView view) {
        this.model = model;
        this.iModel = iModel;
        this.view = view;
        this.currentState = State.READY;
        setupHandlers();
    }

    private void setupHandlers() {
        view.getCanvas().setOnMouseMoved(this::handleMoved);
        view.getCanvas().setOnMousePressed(this::handlePressed);
        view.getCanvas().setOnMouseDragged(this::handleDragged);
        view.getCanvas().setOnMouseReleased(this::handleReleased);
        view.getDealButton().setOnAction(e -> handleDeal());
        view.getHitButton().setOnAction(e -> handleHit());
        view.getStandButton().setOnAction(e -> handleStand());
    }

    private void handleDeal() {
        model.deal();
        model.notifySubscribers();
    }

    private void handleHit() {
        model.hit();
        model.notifySubscribers();
    }

    private void handleStand() {
        model.stand();
        model.notifySubscribers();
    }



    private void handleMoved(MouseEvent e) {
        if (currentState == State.READY) {
            Stackable hovered = null;
            for (Stackable s : model.getStackables()) {
                if (s instanceof Chip) {
                    if (s.onElement(e.getX(), e.getY())) {
                        hovered = s;
                        break;
                    }
                } else if (s instanceof ChipStack) {
                    for (Stackable child : s.getChildren().reversed()) {
                        if (child.onElement(e.getX(), e.getY())) {
                            hovered = child;
                            break;
                        }
                    }
                }
            }
            iModel.setHoveredComponent((Chip)hovered);
        }
    }

    private void handlePressed(MouseEvent e) {
        if (currentState == State.READY) {
            for (Stackable s : model.getStackables()) {
                if (s instanceof ChipStack stack) {
                    List<Stackable> children = stack.getChildren();
                    for (int i = children.size() - 1; i >= 0; i--) {
                        Stackable child = children.get(i);
                        if (child.onElement(e.getX(), e.getY())) {
                            if (i == 0) {
                                // Bottom chip – treat like normal
                                iModel.setSelectedComponent(stack);
                            } else {
                                // Not bottom chip – split!
                                ChipStack newStack = stack.split(i);
                                model.addStackable(newStack);
                                iModel.setSelectedComponent(newStack);
                            }
                            lastX = e.getX();
                            lastY = e.getY();
                            currentState = State.DRAGGING;
                            model.notifySubscribers();
                            return;
                        }
                    }
                } else {
                    // Single chip
                    if (s.onElement(e.getX(), e.getY())) {
                        iModel.setSelectedComponent(s);
                        lastX = e.getX();
                        lastY = e.getY();
                        currentState = State.DRAGGING;
                        model.notifySubscribers();
                        return;
                    }
                }
            }
        }
    }


    private void handleDragged(MouseEvent e) {
        if (currentState == State.DRAGGING) {
            double dx = e.getX() - lastX;
            double dy = e.getY() - lastY;
            iModel.getSelectedComponent().move(dx, dy);
            lastX = e.getX();
            lastY = e.getY();
            model.notifySubscribers();
        }
    }

    private void handleReleased(MouseEvent e) {
        if (currentState == State.DRAGGING) {

            Optional<Stackable> nearest = model.getNearestStackable(e.getX(), e.getY(), iModel.getSelectedComponent());
            System.out.println(nearest);

            // betting position
//            if (model.getBettingPosition().isInside(e.getX(), e.getY())) {
//                model.getBettingPosition().setContents(iModel.getSelectedComponent());
//                iModel.setBettedComponent(iModel.getSelectedComponent());
//
//            }

            if (nearest.isPresent()) {
                Stackable targetChip = nearest.get();
                model.mergeChips(iModel.getSelectedComponent(), targetChip);
            }

            currentState = State.READY;
            iModel.setSelectedComponent(null);
            model.notifySubscribers();
        }
    }
}






