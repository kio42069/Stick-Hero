package com.example.demo2;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Cherry extends ImageView {
    Cherry(double X, Group group){
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource("images/cherry.png")).toString()));
        this.setX(X);
        this.setY(610);
        this.setFitHeight(30);this.setFitWidth(30);
        group.getChildren().add(this);
    }
}
