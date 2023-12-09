package com.example.demo2;

public class StickGenerator {
    // Design patterns: Factory and Singleton
    private static StickGenerator stickGenerator = null;

    public static StickGenerator getInstance(){
        if(stickGenerator == null){
             stickGenerator = new StickGenerator();
        }
        return stickGenerator;
    }

    public Stick generateStick(){
        return new Stick();
    }

    private StickGenerator(){
        System.err.println("Stick Generator created");
    }
}
