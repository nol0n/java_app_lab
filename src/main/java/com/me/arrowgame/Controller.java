package com.me.arrowgame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.me.arrowgame.GameLoop;

import java.util.HashMap;

public class Controller {
    private GameLoop _game;
    private HashMap<Long, Node> _game_objects = new HashMap<Long, Node>();
    @FXML
    private Pane game_field;

    @FXML
    private Label score;

    @FXML
    private Label shoots;

    public Controller(GameLoop game) {
        _game = game;
    }

    @FXML
    private void start_game() {
        _game.start();
    }

    @FXML
    private void pause_game() { _game.pause(); }

    @FXML
    private void stop_game() { _game.stop(); }

    @FXML
    private void shoot() { _game.shoot(); }

    @FXML
    private void change_player_position(MouseEvent event) {
        _game.change_player_position(event.getY());
    }

    public void add_circle(long id, double x, double y, double radius, String color) {
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.web(color));
        circle.setStroke(Color.web("#000000"));
        circle.setStrokeWidth(1);
        _game_objects.put(id, circle);
        Platform.runLater(() -> { game_field.getChildren().add(_game_objects.get(id)); });
    }

    public void add_polygon(long id, double... points) {
        Polygon polygon = new Polygon(points);
        polygon.setFill(Color.web("#aed8ff"));
        polygon.setStroke(Color.web("#000000"));
        polygon.setStrokeWidth(1);
        _game_objects.put(id, polygon);
        Platform.runLater(() -> game_field.getChildren().add(_game_objects.get(id)));
    }

    public void remove_object(long id) {
        Node tmp = _game_objects.get(id);
        Platform.runLater(() -> game_field.getChildren().remove(tmp));
    }

    public void update_view(long id, double x, double y) {
        Platform.runLater(() -> {
            Node tmp = _game_objects.get(id);
            tmp.setLayoutX(x - tmp.getLayoutBounds().getCenterX());
            tmp.setLayoutY(y - tmp.getLayoutBounds().getCenterY());
        });
    }

    public void change_score(int _score) {
        Platform.runLater(() -> score.setText(Integer.toString(_score)));
    }

    public void change_shoots(int _shoots) {
        Platform.runLater(() -> shoots.setText(Integer.toString(_shoots)));
    }
}