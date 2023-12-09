package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    // Design patterns used: Factory and Singleton
    // See StickGenerator.java for more info
    @Override
    public void start(Stage stage) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();

        Result result = JUnitCore.runClasses(JUnitTestClass.class);
        for(Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());

        File file = new File("bg.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        clip.loop(50);

    }

    public static void main(String[] args) {
        launch(args);
    }
}