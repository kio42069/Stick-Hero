package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Menu {
    @FXML
    private Label welcomeText;
    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    protected void onPlayButtonClick() {
//        System.out.println("it works??");
//        welcomeText.setText("Welcome to JavaFX Application!");

    }

    @FXML
    protected void onExitButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}