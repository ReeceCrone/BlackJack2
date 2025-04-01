package com.example.blackjack2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainUI extends Application {
    @Override
    public void start(Stage stage) {
        Model model = new Model();
        InteractionModel iModel = new InteractionModel();
        TableView view = new TableView();
        Controller controller = new Controller(model, iModel, view);

        view.setModel(model);
        view.setInteractionModel(iModel);

        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.setTitle("Blackjack Chips");
        stage.show();

        // Test: Add some chips and stacks
        Chip chip1 = new Chip(10, 100, 100);
        Chip chip2 = new Chip(20, 180, 100);
        ChipStack stack = new ChipStack(new ArrayList<>(List.of(chip1, chip2)));

        model.addStackable(stack);
    }

    public static void main(String[] args) {
        launch();
    }
}

