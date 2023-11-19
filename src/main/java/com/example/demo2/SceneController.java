package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    @FXML
    private Stage stage;

    @FXML
    private boolean gameIsRunning = false;

    @FXML
    private javafx.scene.control.Label pausedLabel = new Label();

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
    public void switchToMainMenuFromGame() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ESCAPE -> {
                        if(gameIsRunning){
                            System.out.println("pause game eyus");
                            pauseGame();
                        }
                        else{
                            System.out.println("resume");
                            resumeGame();
                        }

                    }
                    case Q -> {
                        try {
                            switchToMainMenuFromGame();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case C -> System.out.println("creating bridge");
                    case X -> System.out.println("switching sides");
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void onExitButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void pauseGame(){
        System.out.println("test 1 ");
        pausedLabel.setVisible(true);
        gameIsRunning = false;
    }

    @FXML
    private void resumeGame(){
        System.out.println("test 2 ");
        pausedLabel.setVisible(false);
        gameIsRunning = true;
    }



}
