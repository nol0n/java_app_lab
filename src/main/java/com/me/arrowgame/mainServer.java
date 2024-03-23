package com.me.arrowgame;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mainServer implements IConnectionListener {
    Model model = BModel.build();
    InetAddress ip;
    int port = 1234;

    final int MAX_PLAYERS = 1;
    Map<Integer, Boolean> playerSlots = new HashMap<Integer, Boolean>();
    public mainServer() {
        for (int i = 1; i <= MAX_PLAYERS; i++) {
            playerSlots.put(i, false);
        }
    }

    public void start() {
        ServerSocket sockServer;
        Socket sock;

        try {
            ip = InetAddress.getLocalHost();
            sockServer = new ServerSocket(port, 0, ip);
            System.out.println("Server running on port: " + port);

            while (true) {
                sock = sockServer.accept();
                int playerId = assignPlayerId();
                if (playerId == -1) {
                    System.out.println("Maximum players reached. Connection refused.");
                    SocketClient socketClient = new SocketClient(sock, true, playerId, this);
                    continue;
                }

                SocketClient socketClient = new SocketClient(sock, true, playerId, this);
                System.out.println("Player " + playerId + " connected, port: " + sock.getPort());
                model.addObserver(playerId,
                        (m) -> {
                            Response resp = new Response(m.getObjects(), null, responseAction.UPDATE_MODEL);
                            socketClient.sendResponse(resp);
                        }
                );
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }

    private int assignPlayerId() {
        for (Map.Entry<Integer, Boolean> entry : playerSlots.entrySet()) {
            if (!entry.getValue()) {
                playerSlots.put(entry.getKey(), true);
                return entry.getKey();
            }
        }
        return -1;
    }

    public void releasePlayerId(int playerId) {
        playerSlots.put(playerId, false);
    }

    public static void main(String[] args) {
        mainServer server = new mainServer();
        server.start();
    }

    @Override
    public void onConnectionLost(int playerId) {
        System.out.println("Player " + playerId + " has disconnected");
        playerSlots.put(playerId, false);
    }
}
