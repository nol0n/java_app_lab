package com.me.arrowgame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class _Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(_Application.class.getResource("game-view.fxml"));

        _GameLoop game_loop = new _GameLoop();
        _Controller controller = new _Controller(game_loop);
        _Game game = new _Game(game_loop);
        game_loop.init(controller, game);
        fxmlLoader.setController(controller);

        Scene scene = new Scene(fxmlLoader.load(), 800, 550);
        stage.setTitle("Arrow");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}