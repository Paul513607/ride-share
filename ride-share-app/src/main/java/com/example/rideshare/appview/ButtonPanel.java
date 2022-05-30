package com.example.rideshare.appview;

import com.example.rideshare.model.Route;
import com.example.rideshare.model.Station;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class ButtonPanel {
    private final MainFrame mainFrame;
    private HBox buttonLayout;

    private List<PathTransition> animations = new ArrayList<>();

    private boolean isStarted = false;

    public ButtonPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        display();
    }

    public void display() {
        buttonLayout = new HBox();

        Button generateBtn = new Button();
        generateBtn.setText("Generate instance");
        generateBtn.setPrefSize(200, 50);
        generateBtn.setOnAction(actionEvent -> generateNewInstance());

        Button startBtn = new Button();
        startBtn.setText("Start / Stop");
        startBtn.setPrefSize(200, 50);
        startBtn.setOnAction(actionEvent -> startOrStopTheInstance());

        buttonLayout.paddingProperty().setValue(new Insets(10, 10, 50, 10));
        buttonLayout.spacingProperty().setValue(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(generateBtn, startBtn);
    }

    private void generateNewInstance() {
        DrawingPanel tempDrawingPanel = new DrawingPanel(mainFrame, mainFrame.generateGraph());
        mainFrame.setDrawingPanel(tempDrawingPanel);
        mainFrame.getRoot().setCenter(tempDrawingPanel.getDrawingLayout());
    }

    private void startOrStopTheInstance() {
        if (!isStarted) {
            isStarted = true;
            Random random = new Random();
            for (Station node : mainFrame.getDrawingPanel().getGraph().getNodes()) {
                for (int bikeCount = 0; bikeCount < node.getBikesStationed(); ++bikeCount) {
                    Circle circle = new Circle(node.getX(), node.getY(), DrawingPanel.CIRCLE_RADIUS / 2);
                    circle.setFill(Color.WHITE);
                    circle.setStroke(Color.GREY);
                    // Get the next station
                    Long id = random.nextLong(1, 7);
                    while (id.equals(node.getId())) {
                        id = random.nextLong(1, 7);
                    }
                    Station nextNode = mainFrame.getDrawingPanel().getGraph().findStationById(id);

                    node.setBikesStationed(node.getBikesStationed() - 1);
                    nextNode.setBikesStationed(nextNode.getBikesStationed() + 1);
                    // Get the route
                    Route route = mainFrame.getDrawingPanel().getGraph().findRouteForNodes(node, nextNode);
                    Line line = new Line(node.getX(), node.getY(), nextNode.getX(), nextNode.getY());

                    // Start the animation
                    PathTransition pathTransition = new PathTransition();
                    pathTransition.setNode(circle);
                    pathTransition.setPath(line);
                    pathTransition.setDuration(Duration.seconds(route.getLength() / DrawingPanel.BIKE_SPEED));
                    pathTransition.setCycleCount(1);
                    animations.add(pathTransition);
                    pathTransition.play();

                    mainFrame.getDrawingPanel().getDrawingLayout().getChildren().add(circle);
                }
            }
            mainFrame.getRoot().setCenter(mainFrame.getDrawingPanel().getDrawingLayout());
        } else {
            isStarted = false;
            for (PathTransition animation : animations) {
                animation.stop();
            }

            Station depot = mainFrame.getDrawingPanel().getGraph().findDepot();
            Circle circle = new Circle(depot.getX(), depot.getY(), DrawingPanel.CIRCLE_RADIUS - 3);
            circle.setFill(Color.GREEN);

            List<Long> ids = new ArrayList<>();
            ids.add(depot.getId());

            Random random = new Random();
            int howMany = 3;
            Double[] points = new Double[howMany * 2 + 4];
            int index = 0;
            points[index] = depot.getX(); index++;
            points[index] = depot.getY(); index++;

            for (int count = 1; count <= howMany; count++) {
                long id = random.nextLong(1, 7);
                while (ids.contains(id)) {
                    id = random.nextLong(1, 7);
                }
                ids.add(id);
                Station nextNode = mainFrame.getDrawingPanel().getGraph().findStationById(id);

                points[index] = nextNode.getX(); index++;
                points[index] = nextNode.getY(); index++;
            }

            points[index] = depot.getX(); index++;
            points[index] = depot.getY();

            Polyline path = new Polyline();
            path.getPoints().addAll(points);

            PathTransition pathTransition = new PathTransition();
            pathTransition.setNode(circle);
            pathTransition.setPath(path);
            pathTransition.setDuration(Duration.seconds(10));
            pathTransition.setCycleCount(1);
            pathTransition.play();

            mainFrame.getDrawingPanel().getDrawingLayout().getChildren().add(circle);
        }
    }
}
