package com.example.blackjack2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BlackJackApp extends Application {

    @Override
    public void start(Stage stage) {
        MainUI root = new MainUI();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Blackjack");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
