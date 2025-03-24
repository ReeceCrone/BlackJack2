module com.example.blackjack2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.blackjack2 to javafx.fxml;
    exports com.example.blackjack2;
}