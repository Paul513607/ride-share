package com.example.rideshare.appview;

import com.example.rideshare.model.Graph;
import com.example.rideshare.model.Station;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrawingPanel {
    public static final double CIRCLE_RADIUS = 10.0;
    public static final double BIKE_SPEED = 50.0;

    private final MainFrame mainFrame;
    private Group drawingLayout;

    private Graph graph;

    public DrawingPanel(MainFrame mainFrame, Graph graph) {
        this.mainFrame = mainFrame;
        this.graph = graph;
        display();
    }

    public void display() {
        drawingLayout = new Group();

        for (Station node : graph.getNodes()) {
            Circle circle = new Circle();
            circle.setCenterX(node.getX());
            circle.setCenterY(node.getY());
            circle.setRadius(CIRCLE_RADIUS);
            circle.setFill(Color.BLACK);
            if (node.isDepo()) {
                circle.setFill(Color.ORANGE);
            }

            Text text = new Text(node.getX() - 5, node.getY() + 25, node.getId().toString());
            text.setStyle("-fx-text-fill: blue;");

            drawingLayout.getChildren().addAll(circle, text);
        }

        for (Station node : graph.getNodes()) {
            for (Station node1 : graph.getNodes()) {
                if (node.equals(node1)) {
                    continue;
                }

                Line line = new Line(node.getX(), node.getY(), node1.getX(), node1.getY());
                line.setOpacity(0.5);
                drawingLayout.getChildren().add(line);
            }
        }
    }
}
