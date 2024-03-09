package com.me.arrowgame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("game-view.fxml"));

        GameLoop game_loop = new GameLoop();
        Controller controller = new Controller(game_loop);
        Game game = new Game(game_loop);
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