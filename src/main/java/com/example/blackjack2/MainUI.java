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

    }

    public static void main(String[] args) {
        launch();
    }
}

