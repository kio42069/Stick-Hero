package com.example.demo2;


import com.almasb.fxgl.entity.action.Action;
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

import javax.sound.sampled.*;
import java.io.*;
import java.io.File;
import java.util.Objects;
import java.util.Random;

public class SceneController {

    private SaveData saveData = new SaveData(0,0,0);

    private ObjectOutputStream saveOutput = null;
    private ObjectInputStream saveInput = null;

    public StickGenerator stickGenerator = null;

    public Group globalGroup;

    public void createNewStick(){
        this.stick = stickGenerator.generateStick();
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

    private int maxScore = 0;

    public int getMaxScore() {
        return maxScore;
    }

    public Text maxScoreText;

    private Cherry cherry = new Cherry(-1000, new Group());

    public void setCherryScore(int cherryScore) {
        this.cherryScore = cherryScore;
        saveData.setCherries(cherryScore);
        writeSaveData();
    }

    private int cherryScore = 0;


    private Text cherryScoreText = null;

    public void increaseScore(){
        this.score++;
        if(this.score > maxScore)
            maxScore = this.score;
        saveData.setCurrentScore(score);
        saveData.setMaxScore(maxScore);
        writeSaveData();
    }

    @FXML
    private Stage stage;

    @FXML
    private boolean gameIsRunning = false;

    @FXML
    private javafx.scene.control.Label pausedLabel = new Label();

    @FXML
    private Scene scene;


    @FXML private Scene gameScene;

    @FXML
    private Scene pausedScene;

    private Scene gameOverScene;

    @FXML
    private Parent root;


    @FXML
    private javafx.scene.control.Button closeButton;


    public void hideCherry(){
        cherry.toBack();
    }

    public Hero getHeroImage() {
        return heroImage;
    }

    public Cherry getCherry() {
        return cherry;
    }

    public int getCherryScore(){
        return cherryScore;
    }

    private void checkCollisions() {
        javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                if (heroImage.getBoundsInParent().intersects(cherry.getBoundsInParent())) {
                    if(!cherry.isGrabbed()){
                        File file = new File("cherry.wav");
                        AudioInputStream audioInputStream = null;
                        try {
                            audioInputStream = AudioSystem.getAudioInputStream(file);
                        } catch (UnsupportedAudioFileException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Clip clip = null;
                        try {
                            clip = AudioSystem.getClip();
                        } catch (LineUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            clip.open(audioInputStream);
                        } catch (LineUnavailableException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        clip.start();

                        cherry.setGrabbed(true);
                        cherry.setVisible(false);
                        setCherryScore(cherryScore + 1);
                        cherryScoreText.setText(Integer.toString(cherryScore));
                    }
                }
                if(heroImage.getBoundsInParent().intersects(nextPillar.getBoundsInParent())){
                    if(heroImage.getHeroFlipState() == HeroFlipState.FLIPPED){
                        try {
                            if(gameIsRunning){
                                switchToGameOverScene();
                                gameIsRunning = false;
                            }
                        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };
        timer.start();
    }


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
        ret.setX(300+random.nextInt(200));
        ret.setY(600);
        ret.setWidth(70+random.nextInt(130));
        ret.setHeight(400);
        group.getChildren().add(ret);
        return ret;
    }

    public void resetPillars(Group group){
        this.pillar = this.nextPillar;
        this.nextPillar = createPillar(group);
        Random random = new Random();
        cherry = new Cherry(random.nextDouble(200, nextPillar.getX() - 40), group);
    }

    private void stickfall(Group grp){
        stick.fallDown(heroImage, stick.getHeight(), pillar, nextPillar, this, grp);
        gameState = GameState.HERO_IDLE;
    }

    @FXML
    public void switchToMainMenu() throws IOException {
        gameIsRunning = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        scene = new Scene(root);
        pausedScene = scene;
    }

    private void newGame(boolean hasSave, ActionEvent event) throws IOException{

        gameIsRunning = true;

        try {
            saveInput = new ObjectInputStream(new FileInputStream("testsave.bin"));
            saveData = (SaveData) saveInput.readObject();
        } catch (FileNotFoundException e){
            saveData = new SaveData(0,0,0);
        } catch (ClassNotFoundException e) {
            System.err.println("Error");
        } catch (IOException e) {
            System.err.println("IOexception");
        } finally {
            if(saveInput != null) {
                try {
                    saveInput.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(hasSave){
            saveData.setCurrentScore(0);
        }else{
            score = saveData.getCurrentScore();
        }
        writeSaveData();
        cherryScore = saveData.getCherries();
        maxScore = saveData.getMaxScore();

        stickGenerator = StickGenerator.getInstance();
        gameIsRunning = true;
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
        scoreText.setText(Integer.toString(score));
        scoreText.setX(330); scoreText.setY(200);
        scoreText.setFont(Font.font("Comic Sans", 200));

        // HIGH SCORE
        maxScoreText = new Text();
        maxScoreText.setText("MAX: " + maxScore);
        maxScoreText.setX(600); maxScoreText.setY(90);
        maxScoreText.setFont(Font.font("Comic Sans", 50));

        // CHERRY SCORE
        cherryScoreText = new Text();
        cherryScoreText.setText(Integer.toString(cherryScore));
        cherryScoreText.setX(100); cherryScoreText.setY(70);
        cherryScoreText.setFont(Font.font("Comic Sans", 60));

        // CHERRY ICON FOR SCORE
        Image cherryImage = new Image(Objects.requireNonNull(getClass().getResource("images/cherry.png")).toString());
        ImageView cherryImageView = new ImageView();
        cherryImageView.setImage(cherryImage);
        cherryImageView.setX(20);cherryImageView.setY(20);
        cherryImageView.setFitWidth(60);cherryImageView.setFitHeight(60);


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

        group.getChildren().addAll(bgImageView, cornerText, scoreText, pillar, heroImage, stick, cherryImageView, cherryScoreText, maxScoreText);
        checkCollisions();
        nextPillar = createPillar(group);
        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent keyEvent){
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

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ESCAPE -> {
                        if(gameState == GameState.HERO_IDLE){
                            System.out.println("paused game");
                            try {
                                switchToPausedScene();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
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
                    case SPACE -> {
                        if(gameState == GameState.HERO_IDLE || gameState == GameState.STICK_GROWING){
                            gameState = GameState.STICK_GROWING;
                            stick.increaseLength();
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
    public void switchToGame(ActionEvent event) throws IOException {
        newGame(true, event);
    }

    public void writeSaveData() {
        try {
            saveOutput = new ObjectOutputStream(new FileOutputStream("testsave.bin"));
            saveOutput.writeObject(saveData);
        } catch (IOException e) {
            System.err.println("Error while writing save file");
        } finally {
            if(saveOutput != null) {
                try {
                    saveOutput.close();
                } catch (IOException e) {
                    System.err.println("Error in closing output stream");
                }
            }
        }
    }

    public void switchToGameOverScene() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File("boom.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        score = 0;
        saveData.setCurrentScore(0);
        writeSaveData();
        gameIsRunning = false;
        if(null == gameOverScene){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game-over-view.fxml")));
            scene = new Scene(root);
            gameOverScene = scene;
        }else{
            scene = gameOverScene;
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
        gameIsRunning = true;
        scene = gameScene;
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
        gameIsRunning = false;
    }

    @FXML
    private void resumeGame(){
        gameIsRunning = true;
    }

    @FXML
    private void loadGame(ActionEvent event) throws IOException {
        newGame(false, event);
    }

    @FXML
    private void revive(){}

}
