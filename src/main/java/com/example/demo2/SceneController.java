package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    // TODO: death
    // TODO: death screen
    // TODO: cherries
    // TODO: REVIVAL HOLY SHIT

    // TODO: "poisonous cherries"
    public StickGenerator stickGenerator = null;

    public Group globalGroup;

    public void createNewStick(){
        this.stick = stickGenerator.generateStick();
        // OO
    }

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

    public Stick getStick() {
        return stick;
    }

    public void setStick(Stick stick) {
        this.stick = stick;
    }

    private Stick stick = null;

    public Text scoreText;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score = 0;

    public void increaseScore(){
        this.score++;
    }

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

    public Rectangle createPillar(Group group){
        // should end at 190 after animation
        Rectangle ret = new Rectangle();
        Random random = new Random();
        ret.setX(300+random.nextInt(200));
        ret.setY(600);
        ret.setWidth(70+random.nextInt(130));
        ret.setHeight(400);
        group.getChildren().add(ret);
        return ret;
    }

    // lil bro does not wanna work :madge:
    public void resetPillars(Group group){
        this.pillar = this.nextPillar;
        this.nextPillar = createPillar(group);
    }

    private void stickfall(Group grp){
        stick.fallDown(heroImage, stick.getHeight(), pillar, nextPillar, this, grp);
        gameState = GameState.HERO_IDLE;
    }

    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        stickGenerator = StickGenerator.getInstance();
        gameIsRunning = true;
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game-view.fxml")));
        Group group = new Group();
        this.globalGroup = group;
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

        // SCORE
        scoreText = new Text();
        scoreText.setText("SCORE: " + 0);
        scoreText.setX(250); scoreText.setY(100);
        scoreText.setFont(Font.font("Comic Sans", 60));


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
        heroImage.setX(130);heroImage.setY(540);
        heroImage.setFitWidth(60);heroImage.setFitHeight(60);
        heroImage.toFront();

        // STICK
        createNewStick();
//        stick.setX(180);stick.setY(600);
//        stick.setWidth(10);stick.setHeight(10);


        group.getChildren().add(bgImageView);
        group.getChildren().add(cornerText);
        group.getChildren().add(scoreText);
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
                            stickfall(group);
                            gameState = GameState.ANIMATION;
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
                            score = 0;
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


    public void switchToGameOverScene() throws IOException {

        // TODO: revive and reset button

        gameIsRunning = false;
        if(null == pausedScene){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game-over-view.fxml")));
            scene = new Scene(root);
            pausedScene = scene;
        }else{
            scene = pausedScene;
        }
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.SPACE) {
                    try {
                        switchToMainMenuFromGame();
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
