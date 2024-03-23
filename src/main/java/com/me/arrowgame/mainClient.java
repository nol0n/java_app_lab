package com.me.arrowgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainClient extends Application {
    private static Stage currentStage;

    static public SocketClient mySocket;
    @Override
    public void start(Stage stage) throws Exception {
        currentStage = stage;
        showStartView();
    }

    public static void showStartView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(mainClient.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 550);
        currentStage.setTitle("ArrowMP");
        currentStage.setScene(scene);
        currentStage.show();
    }

    public static void showMainView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(mainClient.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 550);
        currentStage.setTitle("ArrowMP");
        currentStage.setScene(scene);
        currentStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}