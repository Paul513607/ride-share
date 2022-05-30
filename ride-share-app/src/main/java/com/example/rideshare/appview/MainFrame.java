package com.example.rideshare.appview;

import com.example.rideshare.model.Graph;
import com.example.rideshare.model.Route;
import com.example.rideshare.model.Station;
import com.github.javafaker.Faker;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.*;

import java.util.Random;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MainFrame extends Application {
    public static final Double WINDOW_WIDTH = 1600.0;
    public static final Double WINDOW_HEIGHT = 1000.0;

    private ButtonPanel buttonPanel;
    private DrawingPanel drawingPanel;

    private Stage window;
    private BorderPane root;

    public void startMainFrame(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // setUp the main stage and pane
        this.window = primaryStage;
        this.window.setTitle("Ride-share application");
        root = new BorderPane();

        buttonPanel = new ButtonPanel(this);
        drawingPanel = new DrawingPanel(this, generateGraph());

        Group layoutCenter = drawingPanel.getDrawingLayout();
        root.setCenter(layoutCenter);

        HBox layoutBottom = buttonPanel.getButtonLayout();
        root.setBottom(layoutBottom);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setScene(scene);
        window.show();
    }

    public Graph generateGraph() {
        Graph graph = new Graph();

        Random random = new Random();
        Faker faker = new Faker();
        for (int count = 1; count <= 6; ++count) {
            graph.addNode(new Station((long) count, faker.name().name(),
                    random.nextDouble(WINDOW_WIDTH / 8, WINDOW_WIDTH - WINDOW_WIDTH / 8),
                    random.nextDouble(WINDOW_HEIGHT / 6, WINDOW_HEIGHT - WINDOW_HEIGHT / 6),
                    0L, random.nextLong(1, 6), 0L, false));
        }
        graph.getNodes().stream().toList().get(1).setDepo(true);

        return graph;
    }
}
