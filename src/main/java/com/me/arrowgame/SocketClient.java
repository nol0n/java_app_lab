package com.me.arrowgame;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    Model model = BModel.build();
    Socket cs;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    ConnectionState state = ConnectionState.WAITING;

    private int socketId;
    private IConnectionListener connectionLostListener;

    public int getId() {
        return socketId;
    }

    public boolean running = false;
/*    RuntimeTypeAdapterFactory<IGameObject> gameObjectTypeFactory = RuntimeTypeAdapterFactory
            .of(IGameObject.class, "type")
            .registerSubtype(Point.class)
            .registerSubtype(Square.class);
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(gameObjectTypeFactory)
                .create();*/

    Gson gson = new Gson();
    boolean isServer = true;

    public SocketClient(Socket cs, boolean isServer, int socketId,
                        IConnectionListener listener) {
        this.cs = cs;
        this.isServer = isServer;
        this.socketId = socketId;
        this.connectionLostListener = listener;
        try {
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);

        } catch (IOException e) {
            System.out.println("Error SocketClient(Socket cs)");
        }

        running = true;

        if (isServer) {
            if (socketId == -1) {
                Response resp = new Response(null, "Maximum players reached. Connection refused.",
                        responseAction.CONNECTION_REFUSED);
                sendResponse(resp);
            } else {
                Response resp = new Response(null, "Connected to server",
                        responseAction.CONNECTION_ACCEPTED);
                sendResponse(resp);
                new Thread(() -> {
                    run();
                }).start();
            }
        } else {
            new Thread(() -> {
                run();
            }).start();
        }
    }

    void run() {
        try {
            is = cs.getInputStream();
        } catch (IOException e) {
            System.out.println("Error run()");
        }
        dis = new DataInputStream(is);

        while (running) {
            try {
                if (isServer) {
                    Message msg = readMessage();
                    switch (msg.getAction()) {
                        case GET -> {
                            Response resp = new Response(model.getObjects(), null, responseAction.UPDATE_MODEL);
                            sendResponse(resp);
                        }
                        case ADD -> {
                            for (Point pnt : msg.getObjects()) {
                                model.add(pnt);
                            }
                        }
                    }
                } else {
                    Response resp = readResponse();
                    switch (resp.getAction()) {
                        case UPDATE_MODEL -> {
                            model.set(resp.getObjects());
                        }
                        case CONNECTION_ACCEPTED -> {
                            System.out.println(resp.getMessage());
                            state = ConnectionState.ACCEPTED;
                        }
                        case CONNECTION_REFUSED -> {
                            System.out.println(resp.getMessage());
                            state = ConnectionState.REFUSED;
                            try {
                                this.cs.close();
                                running = false;
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                connectionLostListener.onConnectionLost(this.getId());
                try {
                    this.cs.close();
                    model.removeObserver(this.getId());
                    running = false;
                    state = ConnectionState.WAITING;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public Response readResponse() {
        Response resp = null;
        try {
            String responseStr = dis.readUTF();
            resp = gson.fromJson(responseStr, Response.class);
        } catch (IOException e) {
            System.out.println("Error while reading response");
        }
        return resp;
    }

    public Message readMessage() {
        Message msg = null;
        try {
            String messageStr = dis.readUTF();
            msg = gson.fromJson(messageStr, Message.class);
        } catch (IOException e) {
            System.out.println("Error while reading message");
        }
        return msg;
    }

    public void sendMessage(Message msg) {
        String strMessage = gson.toJson(msg);
        try {
            dos.writeUTF(strMessage);
            dos.flush();
        } catch (IOException e) {
            System.out.println("Error while sending message");
        }
    }

    public void sendResponse(Response resp) {
        String strResponse = gson.toJson(resp);
        try {
            dos.writeUTF(strResponse);
            dos.flush();
        } catch (IOException e) {
            System.out.println("Error while sending response");
        }
    }
}
