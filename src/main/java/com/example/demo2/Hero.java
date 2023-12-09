package com.example.demo2;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Hero extends ImageView {

    public HeroFlipState getHeroFlipState() {
        return heroFlipState;
    }

    public void setHeroFlipState(HeroFlipState heroFlipState) {
        this.heroFlipState = heroFlipState;
    }

    private HeroFlipState heroFlipState = HeroFlipState.STRAIGHT;

    public void flip(){

        if(heroFlipState == HeroFlipState.STRAIGHT){
            heroFlipState = HeroFlipState.FLIPPED;
            this.setY(540);
            this.setScaleY(-1);
            this.getTransforms().add(new Translate(0, -60));
        }else{
            this.setY(540);
            heroFlipState = HeroFlipState.STRAIGHT;
            this.setScaleY(1);
            this.getTransforms().add(new Translate(0, 60));
        }
    }

    private void moveBackAndResetPillars(double height, Stick stick, Rectangle pillar, Rectangle nextPillar, SceneController sc, Group grp){
        sc.hideCherry();
        Hero hero = this;

        TranslateTransition moveBack = new TranslateTransition(Duration.millis(500), this);
        moveBack.setByX(-height);
        moveBack.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sc.resetPillars(grp);
                sc.setGameState(GameState.HERO_IDLE);

                if(hero.getHeroFlipState() == HeroFlipState.FLIPPED)
                    hero.flip();

                // CREATE NEW STICK
                sc.createNewStick();
                sc.globalGroup.getChildren().add(sc.getStick());

                // stick stuff


//                stick.setHeight(10);stick.setWidth(10);
//                stick.setX(180);stick.setY(600);
//                Rotate rotation = new Rotate();
//                rotation.setPivotX(180);
//                rotation.setPivotY(600);
//                stick.getTransforms().add(rotation);
//                Timeline timeline = new Timeline(
//                        new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)),
//                        new KeyFrame(Duration.millis(50), new KeyValue(rotation.angleProperty(), -90)));

            }
        });
        moveBack.play();

        int oldX = (int) nextPillar.getX();
        int oldWidth = (int) nextPillar.getWidth();

        TranslateTransition moveNextPillar = new TranslateTransition(Duration.millis(500), nextPillar);
        moveNextPillar.setByX(190 - oldX - oldWidth);
        // old x = pillar.x
        // new x = 190 - pillar.width
        // new x - old x = 190 - pillar.x - pillar.width
        moveNextPillar.play();

        //
        TranslateTransition movePillar = new TranslateTransition(Duration.millis(500), pillar);
        movePillar.setByX(190 - oldX - oldWidth);
        movePillar.play();

        // MOVE THE STICK
        TranslateTransition stickMovement = new TranslateTransition(Duration.millis(500), stick);
        stickMovement.setByX(190 - oldX - oldWidth);
        stickMovement.play();
    }

    public void move(double height, Stick stick, Rectangle pillar, Rectangle nextPillar, SceneController sc, Group grp) {
        // TODO: runtime checks for death
        sc.setGameState(GameState.HERO_MOVING);
        Hero hero = this;
        TranslateTransition moveTransition = new TranslateTransition(Duration.millis(500), this);
        moveTransition.setByX(height);
        moveTransition.play();

        moveTransition.setOnFinished(new EventHandler<ActionEvent>() {

            // TODO: if the player finishes flipped, then definitely dead
            @Override
            public void handle(ActionEvent event) {
                // check death here
                boolean dead = false;

                if (hero.getHeroFlipState() == HeroFlipState.FLIPPED) {
                    dead = true;
                }

                int stickHeight = (int)stick.getHeight() + (int)stick.getX();
                int pillarLowerBound = (int)nextPillar.getX();
                int pillarUpperBound = (int)nextPillar.getX() + (int)nextPillar.getWidth();

                if((stickHeight < pillarLowerBound) || (stickHeight > pillarUpperBound)){
                    dead = true;
                } else {
                    sc.increaseScore();
                    sc.scoreText.setText(Integer.toString(sc.getScore()));
                    sc.maxScoreText.setText("MAX: " +  sc.getMaxScore());
                }

                if(dead){
                    try {
                        sc.switchToGameOverScene();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    // TODO: thingies to be done here right here i tell ya kid
                }else{
                    sc.setGameState(GameState.ANIMATION);
                    moveBackAndResetPillars(height, stick, pillar, nextPillar, sc, grp);
                }
            }
        });
    }
    public void fall(){}
    public void grabApple(){}

}