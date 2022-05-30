package com.example.rideshare;

import com.example.rideshare.appview.MainFrame;
import com.example.rideshare.util.Timekeeper;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        Timekeeper timekeeper = Timekeeper.getInstance();
        timekeeper.startTimer();
        Thread tkThread = new Thread(timekeeper);
        tkThread.setDaemon(true);
        tkThread.start();

        MainFrame mainFrame = new MainFrame();
        mainFrame.startMainFrame(args);

        timekeeper.stopTimer();
        timekeeper.displayRunningTime();
    }
}
