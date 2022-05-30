package com.example.rideshare.appview;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Class for adding a pop-up on the screen with a certain title and message. */
public class AlertBox {
    public void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(250);
        window.setHeight(250);

        Label msgLabel = new Label();
        msgLabel.setText(message);

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(actionEvent -> window.close());

        BorderPane root = new BorderPane();
        root.setTop(msgLabel);
        root.setCenter(closeBtn);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }
}
