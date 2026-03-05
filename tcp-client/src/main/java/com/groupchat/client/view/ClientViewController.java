package com.groupchat.client.view;

import com.groupchat.client.model.ClientModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.collections.ListChangeListener;
import javafx.scene.shape.Circle;

public class ClientViewController {
    private final String host;
    private final int port;
    private ClientModel model;
    private TextField inputField;
    private Button sendBtn;
    private Label statusLabel;
    private Circle statusDot;

    public ClientViewController(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public GridPane buildLogin(GridPane root, Runnable onSuccess) {
        root.setHgap(12);
        root.setVgap(12);
        root.setPadding(new Insets(25));
        root.getStyleClass().add("client-root");

        Label title = new Label("Group Chat");
        title.getStyleClass().add("title");
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        userField.setPromptText("Enter username");
        userField.setPrefWidth(220);
        Button connectBtn = new Button("Connect");
        connectBtn.setId("connectBtn");
        Label hint = new Label("Leave blank for read-only mode");
        hint.getStyleClass().add("hint");

        connectBtn.setOnAction(e -> {
            String name = userField.getText();
            connectBtn.setDisable(true);
            connectBtn.setText("Connecting...");
            model = new ClientModel();
            boolean ok = model.connect(host, port, name);
            connectBtn.setDisable(false);
            connectBtn.setText("Connect");
            if (ok) {
                onSuccess.run();
            } else {
                new Alert(Alert.AlertType.ERROR, "Could not connect to " + host + ":" + port + ". Check that the server is running.").showAndWait();
            }
        });

        root.add(title, 0, 0, 2, 1);
        root.add(userLabel, 0, 1);
        root.add(userField, 1, 1);
        root.add(connectBtn, 1, 2);
        root.add(hint, 0, 3, 2, 1);
        root.setAlignment(Pos.CENTER);
        return root;
    }

    public GridPane buildChat(Runnable onDisconnect) {
        GridPane root = new GridPane();
        root.setHgap(12);
        root.setVgap(12);
        root.setPadding(new Insets(15));
        root.getStyleClass().add("chat-root");

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        statusDot = new Circle(6, Color.GREEN);
        statusLabel = new Label("Online");
        statusLabel.getStyleClass().add("status");
        header.getChildren().addAll(statusDot, statusLabel);
        if (model.isReadOnly()) {
            Label ro = new Label("(Read-only)");
            ro.getStyleClass().add("readonly-label");
            header.getChildren().add(ro);
        }
        root.add(header, 0, 0, 2, 1);

        model.setOnDisconnect(() -> {
            statusLabel.setText("Disconnected");
            statusLabel.getStyleClass().remove("status");
            statusLabel.getStyleClass().add("status-disconnected");
            statusDot.setFill(Color.GRAY);
            if (sendBtn != null) sendBtn.setDisable(true);
            if (inputField != null) inputField.setDisable(true);
        });

        ListView<String> messageList = new ListView<>(model.getMessages());
        model.getMessages().addListener((javafx.collections.ListChangeListener.Change<? extends String> c) -> {
            if (messageList.getItems().isEmpty()) return;
            messageList.scrollTo(messageList.getItems().size() - 1);
        });
        messageList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.startsWith("[System]")) {
                        setStyle("-fx-background-color: #e3f2fd; -fx-text-fill: #0d47a1; -fx-padding: 6 10;");
                    } else if (item.startsWith("Active users:")) {
                        setStyle("-fx-background-color: #e8f5e9; -fx-text-fill: #1b5e20; -fx-padding: 6 10;");
                    } else if (item.contains("Disconnected")) {
                        setStyle("-fx-background-color: #ffebee; -fx-text-fill: #b71c1c; -fx-padding: 6 10;");
                    } else {
                        setStyle("-fx-background-color: #fff8e1; -fx-text-fill: #333; -fx-padding: 6 10;");
                    }
                }
            }
        });
        messageList.setPrefHeight(300);
        root.add(messageList, 0, 1, 2, 1);

        HBox inputRow = new HBox(8);
        inputRow.setAlignment(Pos.CENTER_LEFT);
        inputField = new TextField();
        inputField.setPromptText(model.isReadOnly() ? "Read-only mode" : "Message or: allUsers, end, bye");
        inputField.setPrefWidth(400);
        inputField.setDisable(model.isReadOnly());
        sendBtn = new Button("SEND");
        sendBtn.setId("sendBtn");
        sendBtn.setDisable(model.isReadOnly());

        inputField.setOnAction(e -> doSend());
        sendBtn.setOnAction(e -> doSend());

        Button disconnectBtn = new Button("Disconnect");
        disconnectBtn.setOnAction(e -> {
            if (model != null) {
                model.send("bye");
                model.disconnect();
            }
            javafx.application.Platform.exit();
        });
        inputRow.getChildren().addAll(inputField, sendBtn, disconnectBtn);
        root.add(inputRow, 0, 2, 2, 1);

        return root;
    }

    private void doSend() {
        if (model == null || model.isReadOnly()) return;
        String text = inputField.getText();
        if (text == null) return;
        String trimmed = text.trim();
        model.send(trimmed);
        inputField.clear();
        if (trimmed.equalsIgnoreCase("bye") || trimmed.equalsIgnoreCase("end")) {
            model.disconnect();
            javafx.application.Platform.exit();
        }
    }

    public ClientModel getModel() {
        return model;
    }
}
