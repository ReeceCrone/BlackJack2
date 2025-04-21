package com.example.blackjack2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainUI extends StackPane {

    public MainUI() {
        Model model = new Model();
        InteractionModel iModel = new InteractionModel();
        TableView view = new TableView();
        Controller controller = new Controller(model, iModel, view);

        view.setModel(model);
        view.setInteractionModel(iModel);

        view.draw();

        this.getChildren().addAll(view);
    }
}

