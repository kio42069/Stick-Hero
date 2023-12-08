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
        this.setY(this.getY() - 10);
        this.setHeight(this.getHeight() + 10);
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

                int stickHeight = (int)stick.getHeight() + (int)stick.getX();
                int pillarLowerBound = (int)nextPillar.getX();
                int pillarUpperBound = (int)nextPillar.getX() + (int)nextPillar.getWidth();
                if((stickHeight < pillarLowerBound) || (stickHeight > pillarUpperBound)){
                    System.out.println("i shall implement fxml here");
                    // TODO: thingies to be done here right here i tell ya kid

                }
            }
        });
        timeline.play();

    }


}
