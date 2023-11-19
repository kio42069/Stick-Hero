package com.example.demo2;

public class Hero {
    private double xCoord;
    private double yCoord;

    public void move(){}
    public void fall(){}
    public void switchSides(){}
    public void grabApple(){}
    public void createStick(){}
    public void increaseScore(){}
    public void detectCollision(){}
    public void detectIncorrectStickSize(){}

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }
}