package com.example.rideshare.util;

import lombok.Getter;
import lombok.Synchronized;

import java.util.Random;

/** Class for keeping track of the time in game and stopping the game if it has elapsed. */
@Getter(onMethod_ = {@Synchronized})
public class Timekeeper implements Runnable {
    private long startTime;
    private long endTime;
    private double duration;

    private static Timekeeper timekeeperInstance = null;

    private Timekeeper() {
    }

    public static Timekeeper getInstance() {
        if (timekeeperInstance == null)
            timekeeperInstance = new Timekeeper();

        return timekeeperInstance;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void displayRunningTime() {
        System.out.println("Time taken: " + duration);
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
        duration = (double) (endTime - startTime) / 1000000000.0;
    }
}
