package com.groupchat.server;

import com.groupchat.server.config.ServerConfig;
import com.groupchat.server.model.ServerModel;
import com.groupchat.server.view.ServerViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TCPServer extends Application {
    private ServerModel model;

    @Override
    public void start(Stage stage) {
        ServerConfig config = new ServerConfig();
        model = new ServerModel(config);
        try {
            model.start();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Server failed to start: " + e.getMessage()).showAndWait();
            Platform.exit();
            return;
        }
        ServerViewController viewController = new ServerViewController();
        Scene scene = new Scene(viewController.build(model), 600, 400);
        scene.getStylesheets().add(getClass().getResource("/server-style.css").toExternalForm());
        stage.setTitle("TCP Chat Server — port " + model.getActualPort());
        stage.setMinWidth(550);
        stage.setMinHeight(380);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            model.stop();
            Platform.exit();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
