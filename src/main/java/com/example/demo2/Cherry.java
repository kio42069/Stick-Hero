package com.example.demo2;

public class Cherry {
    private boolean collected = false;
    private double distanceFromPlatform;

    public void setCollected(){}

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public double getDistanceFromPlatform() {
        return distanceFromPlatform;
    }

    public void setDistanceFromPlatform(double distanceFromPlatform) {
        this.distanceFromPlatform = distanceFromPlatform;
    }
}
