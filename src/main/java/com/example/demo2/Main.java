package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600, Color.LIGHTSKYBLUE);

//        Image icon = new Image("");
//        stage.getIcons().add(icon);

        stage.setTitle("Stick Hero");
//        stage.setWidth(900);
//        stage.setHeight(900);
        stage.setResizable(false);

//        stage.setFullScreen(true);
//        stage.setFullScreenExitHint("wow lol, oops ho gaya");
//        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));


        // TEXT
        Text titleText = new Text();
        titleText.setFont(Font.font("Roboto Mono", 50));
        titleText.setText("Stick Hero");
        titleText.setY(100);
        titleText.setX(100);
        root.getChildren().add(titleText);

        // LINES
        Line line = new Line();
        line.setStartX(0);
        line.setStartY(100);
        line.setEndX(700);
        line.setEndY(200);
        line.setStrokeWidth(5);
        root.getChildren().add(line);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}