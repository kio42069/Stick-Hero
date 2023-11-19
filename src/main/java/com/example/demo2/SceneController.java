package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private Parent root;


    @FXML
    private javafx.scene.control.Button closeButton;


    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    public void escapeDetect(KeyEvent event) throws IOException {
        System.out.println("test");
        switch (event.getCode()){
            case ESCAPE -> System.out.println("test");
            case Q -> {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            case KP_UP -> System.out.println("creating bridge");
            case KP_DOWN -> System.out.println("switching sides");
        }
    }



    @FXML
    protected void onExitButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}
