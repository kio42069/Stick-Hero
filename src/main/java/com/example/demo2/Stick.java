package com.example.demo2;

import com.almasb.fxgl.entity.action.Action;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Stick extends Rectangle {

    public void increaseLength(){
        this.setY(this.getY() - 10);
        this.setHeight(this.getHeight() + 10);
    }

    private void reset(){
        this.setHeight(10);
    }

    public boolean fallDown(Hero hero, double height, Rectangle pillar, Rectangle nextPillar, SceneController sc, Group grp){
//        RotateTransition fallTransition = new RotateTransition(Duration.millis(500), this);
//        fallTransition.setByAngle(90);
//        fallTransition.play();

        final boolean[] kekw = new boolean[1];

        Stick stick = this;
        Rotate rotation = new Rotate();
        rotation.setPivotX(180);
        rotation.setPivotY(600);
        this.getTransforms().add(rotation);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(rotation.angleProperty(), 90)));
        timeline.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                kekw[0] = hero.move(height, stick, pillar, nextPillar, sc, grp);
            }
        });
        timeline.play();

        return kekw[0];


    }


}
