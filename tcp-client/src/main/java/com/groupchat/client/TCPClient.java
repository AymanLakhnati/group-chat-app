package com.groupchat.client;

import com.groupchat.client.config.ClientConfig;
import com.groupchat.client.model.Protocol;
import com.groupchat.client.view.ClientViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TCPClient extends Application {
    private static String serverHost = "localhost";
    private static int serverPort = 3000;

    @Override
    public void start(Stage stage) {
        ClientViewController controller = new ClientViewController(serverHost, serverPort);
        GridPane loginRoot = new GridPane();
        controller.buildLogin(loginRoot, () -> {
            GridPane chatRoot = controller.buildChat(() -> {});
            Scene chatScene = new Scene(chatRoot, 500, 400);
            chatScene.getStylesheets().add(getClass().getResource("/client-style.css").toExternalForm());
            stage.setScene(chatScene);
            stage.setTitle("Chat - " + controller.getModel().getUsername());
            stage.setMinWidth(480);
            stage.setMinHeight(350);
        });
        Scene loginScene = new Scene(loginRoot, 380, 220);
        loginScene.getStylesheets().add(getClass().getResource("/client-style.css").toExternalForm());
        stage.setScene(loginScene);
        stage.setTitle("Group Chat - Connect");
        stage.setMinWidth(360);
        stage.setMinHeight(200);
        stage.setOnCloseRequest(e -> {
            if (controller.getModel() != null) controller.getModel().disconnect();
            Platform.exit();
        });
        stage.show();
    }

    public static void main(String[] args) {
        try {
            ClientConfig config = new ClientConfig();
            config.load();
            serverHost = config.getHost();
            serverPort = config.getPort();
        } catch (Exception ignored) {}
        if (args.length >= 2) {
            serverHost = args[0].trim();
            try {
                int p = Integer.parseInt(args[1].trim());
                if (p >= Protocol.MIN_PORT && p <= Protocol.MAX_PORT) serverPort = p;
            } catch (NumberFormatException ignored) {}
        }
        launch(args);
    }
}
