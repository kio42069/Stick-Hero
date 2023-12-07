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
import java.util.Objects;
import java.util.Random;

public class SceneController {

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private GameState gameState = GameState.HERO_IDLE;

    private Rectangle pillar = null;
    private Rectangle nextPillar = null;
    private Hero heroImage = null;
    private Stick stick = null;

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

    private boolean tester = true;


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

    public Rectangle createPillar(Group group){
        Rectangle ret = new Rectangle();
        Random random = new Random();
        ret.setX(500);
        ret.setY(600);
        ret.setWidth(random.nextInt(4,12)*10);
        ret.setHeight(400);
        group.getChildren().add(ret);
        return ret;
    }

    // lil bro does not wanna work :madge:
    public void resetPillars(Group group){
        this.pillar = this.nextPillar;
        this.nextPillar = createPillar(group);
    }

    private boolean stickfall(Group grp){
        boolean kekw = stick.fallDown(heroImage, stick.getHeight(), pillar, nextPillar, this, grp);
        gameState = GameState.HERO_IDLE;
        return kekw;
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
        pillar = new Rectangle();
        pillar.setX(20);
        pillar.setY(600);
        pillar.setWidth(170);
        pillar.setHeight(400);

        // SPRITE
        Image spriteImage = new Image(Objects.requireNonNull(getClass().getResource("images/sprite_0.png")).toString());
        heroImage = new Hero();
        heroImage.setImage(spriteImage);
        heroImage.setX(150);heroImage.setY(570);
        heroImage.setFitWidth(30);heroImage.setFitHeight(30);
        heroImage.toFront();

        // STICK
        stick = new Stick();
        stick.setX(180);stick.setY(600);
        stick.setWidth(10);stick.setHeight(10);


        group.getChildren().add(bgImageView);
        group.getChildren().add(cornerText);
        group.getChildren().add(pillar);
        group.getChildren().add(heroImage);
        group.getChildren().add(stick);
        nextPillar = createPillar(group);
        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent keyEvent){
                pressed = false;
                switch(keyEvent.getCode()){
                    case SPACE ->{
                        if(gameState == GameState.STICK_GROWING) {
                            gameState = GameState.ANIMATION;
                            tester = stickfall(group);
                            System.out.println(tester);
                        }
                    }
                }
            }
        });


//        resetPillars(group, pillar, nextPillar);



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
                    case SPACE -> {
                        if(gameState == GameState.HERO_IDLE || gameState == GameState.STICK_GROWING){
                            gameState = GameState.STICK_GROWING;
                            stick.increaseLength();
                            pressed = true;
                        } else if(gameState == GameState.HERO_MOVING){
                            heroImage.flip();
                        }
                    }
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToGameOverScene() throws IOException {
        gameIsRunning = false;
        if(null == pausedScene){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game-over-view.fxml")));
            scene = new Scene(root);
            pausedScene = scene;
        }

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
