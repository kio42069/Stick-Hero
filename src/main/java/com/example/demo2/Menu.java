package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Menu {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}