package com.me.arrowgame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class startController {
    Model model = BModel.build();
    SocketClient socketClient;

    @FXML
    Label info_message;

    @FXML
    void connect_to_server() {
        try {
            mainClient.showMainView();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
