package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    boolean pressed = false;

    @FXML private Scene gameScene;

    @FXML
    private Scene pausedScene;

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
        gameIsRunning = true;
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game-view.fxml")));
        Group group = new Group();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(group, 800, 800);
        gameScene = scene;

        // BACKGROUND IMAGE
        Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("images/background.png")).toString());
        ImageView bgImageView = new ImageView();
        bgImageView.setImage(bgImage);
        bgImageView.setX(0);bgImageView.setY(0);
        bgImageView.setFitWidth(800);bgImageView.setFitHeight(800);

        // CORNER TEXT FOR PAUSE
        Text cornerText = new Text();
        cornerText.setText("Press ESC to pause");
        cornerText.setX(600);
        cornerText.setY(20);
        cornerText.setFont(Font.font("Comic Sans", 20));

        // RECTANGLE
        Rectangle pillar = new Rectangle();
        pillar.setX(20);
        pillar.setY(600);
        pillar.setWidth(170);
        pillar.setHeight(400);

        // SPRITE
        Image spriteImage = new Image(Objects.requireNonNull(getClass().getResource("images/sprite_0.png")).toString());
        Hero heroImage = new Hero();
        heroImage.setImage(spriteImage);
        heroImage.setX(130);heroImage.setY(540);
        heroImage.setFitWidth(60);heroImage.setFitHeight(60);
        heroImage.toFront();

        // STICK
        Rectangle stick = new Rectangle();
        stick.setX(150);stick.setY(550);
        stick.setWidth(10);stick.setHeight(10);



        group.getChildren().add(bgImageView);
        group.getChildren().add(cornerText);
        group.getChildren().add(pillar);
        group.getChildren().add(heroImage);
        group.getChildren().add(stick);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ESCAPE -> {
                        System.out.println("paused game");
                        pauseGame();
                        try {
                            switchToPausedScene();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case Q -> {
                        try {
                            switchToMainMenuFromGame();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case X -> heroImage.flip();
                    case C -> {
                        System.out.println("heh");
                        pressed = true;
                    }
                    // case M -> heroImage.move();
                    default -> pressed = false;
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void switchToPausedScene() throws IOException{
        gameIsRunning = false;
        if(null == pausedScene){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("paused-menu.fxml")));
            scene = new Scene(root);
            pausedScene = scene;
        }else{
            scene = pausedScene;
        }
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ESCAPE) {
                    try {
                        switchToGameSceneFromPaused();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToGameSceneFromPaused() throws IOException{
        scene = gameScene;
        stage.setScene(scene);
        stage.show();
        gameIsRunning = true;
    }


    @FXML
    protected void onExitButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void pauseGame(){
        gameIsRunning = false;
    }

    @FXML
    private void resumeGame(){
        gameIsRunning = true;
    }



}
