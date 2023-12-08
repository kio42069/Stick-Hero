package com.example.demo2;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Stick extends Rectangle {

    Stick(){
        super();
        this.setX(180);
        this.setY(600);
        this.setWidth(10);
        this.setHeight(10);
    }


    public void increaseLength(){
        int xx_hard_xx = 20;
        this.setY(this.getY() - xx_hard_xx);
        this.setHeight(this.getHeight() + xx_hard_xx);
    }

    private void reset(){
        this.setHeight(10);
    }

    public void fallDown(Hero hero, double height, Rectangle pillar, Rectangle nextPillar, SceneController sc, Group grp){

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
                hero.move(height, stick, pillar, nextPillar, sc, grp);

            }
        });
        timeline.play();

    }


}
