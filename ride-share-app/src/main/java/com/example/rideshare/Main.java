package com.example.rideshare;

import com.example.rideshare.appview.MainFrame;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.startMainFrame(args);
    }
}
