package com.example.blackjack2;

import javafx.scene.input.MouseEvent;

public class Controller {
    private Model model;
    private InteractionModel iModel;
    private TableView view;
    private Stackable selected;

    public Controller(Model model, InteractionModel iModel, TableView view) {
        this.model = model;
        this.iModel = iModel;
        this.view = view;
        setupHandlers();
    }

    private void setupHandlers() {
        view.getCanvas().setOnMouseMoved(this::handleMoved);
        view.getCanvas().setOnMousePressed(this::handlePressed);
        view.getCanvas().setOnMouseDragged(this::handleDragged);
    }

    private void handleMoved(MouseEvent e) {
        Stackable hovered = null;
        for (Stackable s : model.getStackables()) {
            if (s.onElement(e.getX(), e.getY())) {
                hovered = s;
                break;
            }
        }
        iModel.setHoveredComponent(hovered);
    }

    private void handlePressed(MouseEvent e) {
        for (Stackable s : model.getStackables()) {
            if (s.onElement(e.getX(), e.getY())) {
                selected = s;
                iModel.setSelectedComponent(s);
                break;
            }
        }
    }

    private void handleDragged(MouseEvent e) {
        if (selected != null) {
            model.moveStackable(selected, e.getX(), e.getY());
        }
    }
}




