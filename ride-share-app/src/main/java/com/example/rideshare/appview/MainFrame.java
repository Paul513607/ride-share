package com.example.rideshare.appview;

import com.example.rideshare.model.Graph;
import com.example.rideshare.model.Route;
import com.example.rideshare.model.Station;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
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
        window.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            // Save file
        });

        window.setScene(scene);
        window.show();
    }

    public List<Station> requestNodes() throws JsonProcessingException {
        String URL = "http://localhost:8081/api/v1/stations/init";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        System.out.println(response.getStatusCode());

        String URL1 = "http://localhost:8081/api/v1/stations";
        RestTemplate restTemplate1 = new RestTemplate();
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request1 = new HttpEntity<>(headers1);

        ResponseEntity<String> response1 = restTemplate1.exchange(URL1, HttpMethod.GET, request1, String.class);
        System.out.println(response1.getStatusCode());
        System.out.println(response1.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        return Arrays.asList(objectMapper.readValue(response1.getBody(), Station[].class));
    }

    public Graph generateGraph() {
        List<Station> stationList = new ArrayList<>();
        try {
            stationList = requestNodes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Graph graph = new Graph();

        for (Station station : stationList) {
            graph.addNode(station);
        }

        /*Random random = new Random();
        Faker faker = new Faker();
        for (int count = 1; count <= 6; ++count) {
            graph.addNode(new Station((long) count, faker.name().name(),
                    random.nextDouble(WINDOW_WIDTH / 8, WINDOW_WIDTH - WINDOW_WIDTH / 8),
                    random.nextDouble(WINDOW_HEIGHT / 6, WINDOW_HEIGHT - WINDOW_HEIGHT / 6),
                    0L, random.nextLong(1, 6), 0L, false));
        }
        graph.getNodes().stream().toList().get(1).setDepo(true);*/

        return graph;
    }

    public void printSolution() {
        String URL = "http://localhost:8081/api/v1/stations/path";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        System.out.println("\n\n\nSolution:");
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        AlertBox alertBox = new AlertBox();
        String solution = Objects.requireNonNull(
                response.getBody())
                    .replace(":[", ":\n[")
                    .replace("],", "],\n");
        alertBox.display("Solution", solution);
    }
}
