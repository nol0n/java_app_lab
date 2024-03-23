package com.me.arrowgame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class mainController implements IObserver, IConnectionListener {
    Model model = BModel.build();

    int observersId = 0;
    @FXML
    Pane view_port;

    @FXML
    public void initialize() {
        model.addObserver(observersId++, this);
    }

    InetAddress ip;
    int port = 1234;

    @FXML
    void connect() {
        if (mainClient.mySocket != null && mainClient.mySocket.running == true) return;

        try {
            Socket sock;
            ip = InetAddress.getLocalHost();
            sock = new Socket(ip, port);

            mainClient.mySocket = new SocketClient(sock, false, observersId++,
                    this);
            while (mainClient.mySocket.state == ConnectionState.WAITING) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            if (mainClient.mySocket.state == ConnectionState.ACCEPTED) {
                mainClient.mySocket.sendMessage(new Message(null, messageAction.GET));
            }
        } catch (IOException e) {
            System.out.println("Client: connection error");
        }
    }

    @FXML
    void mouseEvent(MouseEvent e) {
        if (mainClient.mySocket != null) {
            ArrayList<Point> game_objects = new ArrayList<Point>();
            game_objects.add(new Point(0, e.getX(), e.getY()));
            mainClient.mySocket.sendMessage(new Message(game_objects, messageAction.ADD));
        } else {
            model.add(new Point(0, e.getX(), e.getY()));
        }
    }

    @Override
    public void event(Model m) {
        Platform.runLater(
            () -> {
                view_port.getChildren().clear();
                for (Point pnt : model) {
                    Circle circle = new Circle(pnt.get_x(), pnt.get_y(), 20);
                    circle.setFill(Color.RED);
                    view_port.getChildren().add(circle);
                }
            }
        );
    }

    @Override
    public void onConnectionLost(int playerId) {
        System.out.println("Lost connection to server, port: " + port);
    }
}
