package com.groupchat.server.view;

import com.groupchat.server.model.ServerModel;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerViewController {
    private static final String[] COLORS = {
            "#E8F4FD", "#FFF4E6", "#E8F5E9", "#FCE4EC", "#F3E5F5",
            "#E0F7FA", "#FFF8E1", "#EFEBE9"
    };
    private final Random rnd = new Random();
    private final Map<String, String> userColors = new HashMap<>();

    public GridPane build(ServerModel model) {
        GridPane root = new GridPane();
        root.setHgap(15);
        root.setVgap(15);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("server-root");

        Label title = new Label("Group Chat Server");
        title.getStyleClass().add("title");
        GridPane.setColumnSpan(title, 2);

        VBox usersBox = new VBox(8);
        Label usersLabel = new Label("Connected Users");
        usersLabel.getStyleClass().add("section-label");
        ListView<String> userList = new ListView<>(model.getUsernames());
        userList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-background-color: " + userColors.computeIfAbsent(item, k -> COLORS[rnd.nextInt(COLORS.length)]) + ";");
                }
            }
        });
        userList.setPrefHeight(250);
        usersBox.getChildren().addAll(usersLabel, userList);

        VBox logBox = new VBox(8);
        Label logLabel = new Label("Activity Log");
        logLabel.getStyleClass().add("section-label");
        ListView<String> logList = new ListView<>(model.getLogMessages());
        logList.setPrefHeight(250);
        logList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.contains("Server Started")) {
                        setStyle("-fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32; -fx-padding: 4 8;");
                    } else if (item.contains("Waiting for Client")) {
                        setStyle("-fx-background-color: #e3f2fd; -fx-text-fill: #1565c0; -fx-padding: 4 8;");
                    } else if (item.contains("Welcome")) {
                        setStyle("-fx-background-color: #fff3e0; -fx-text-fill: #e65100; -fx-padding: 4 8;");
                    } else if (item.contains("left") || item.contains("joined")) {
                        setStyle("-fx-background-color: #fce4ec; -fx-text-fill: #880e4f; -fx-padding: 4 8;");
                    } else {
                        setStyle("-fx-background-color: #f5f5f5; -fx-text-fill: #424242; -fx-padding: 4 8;");
                    }
                }
            }
        });
        model.getLogMessages().addListener((javafx.collections.ListChangeListener.Change<? extends String> c) -> {
            if (logList.getItems().isEmpty()) return;
            logList.scrollTo(logList.getItems().size() - 1);
        });
        logBox.getChildren().addAll(logLabel, logList);

        root.add(title, 0, 0);
        root.add(usersBox, 0, 1);
        root.add(logBox, 1, 1);
        root.setAlignment(Pos.TOP_LEFT);
        return root;
    }
}
