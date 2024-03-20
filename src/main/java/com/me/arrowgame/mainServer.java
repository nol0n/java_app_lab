package com.me.arrowgame;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class mainServer {
    Model model = BModel.build();
    InetAddress ip;
    int port = 1234;

    long id = 0;
    public void start() {
        ServerSocket sockServer;
        Socket sock;

        try {
            ip = InetAddress.getLocalHost();
            sockServer = new ServerSocket(port, 0, ip);
            System.out.println("Server running on port: " + port);

            while (true) {
                sock = sockServer.accept();
                System.out.println("Client connected, port: " + sock.getPort());
                SocketClient socketClient = new SocketClient(sock, true);
                model.addObserver(
                        (m) -> {
                            Response resp = new Response(m.getObjects());
                            socketClient.sendResponse(resp);
                        }
                );
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public static void main(String[] args) {
        mainServer server = new mainServer();
        server.start();
    }
}
