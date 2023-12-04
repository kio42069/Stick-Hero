package com.example.demo2;

import javafx.animation.*;
import javafx.geometry.Point3D;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Stick extends Rectangle {

    public void increaseLength(){
        this.setY(this.getY() - 10);
        this.setHeight(this.getHeight() + 10);
    }
    public void fallDown(){
//        RotateTransition fallTransition = new RotateTransition(Duration.millis(500), this);
//        fallTransition.setByAngle(90);
//        fallTransition.play();


        Rotate rotation = new Rotate();
        rotation.setPivotX(180);
        rotation.setPivotY(600);
        this.getTransforms().add(rotation);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(rotation.angleProperty(), 90)));
        timeline.play();


    }


}
