package com.example.blackjack2;

import javafx.scene.layout.StackPane;

public class MainUI extends StackPane {
    public MainUI() {
        Model model = new Model();
        InteractionModel iModel = new InteractionModel();
        TableView tableView = new TableView();
        Controller controller = new Controller();

        model.addSubscriber(tableView);
        iModel.addSubscriber(tableView);
        tableView.setInteractionModel(iModel);
        tableView.setModel(model);
        tableView.setupEvents(controller);
        controller.setModel(model);
        controller.setIModel(iModel);

        this.getChildren().add(tableView);
    }
}
