package com.me.arrowgame;

import com.google.gson.Gson;

import java.io.*;
import java.lang.annotation.Target;
import java.net.Socket;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public class SocketClient {
    Model model = BModel.build();
    Socket cs;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
/*    RuntimeTypeAdapterFactory<IGameObject> gameObjectTypeFactory = RuntimeTypeAdapterFactory
            .of(IGameObject.class, "type")
            .registerSubtype(Point.class)
            .registerSubtype(Square.class);
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(gameObjectTypeFactory)
                .create();*/

    Gson gson = new Gson();
    boolean isServer = true;

    public SocketClient(Socket cs, boolean isServer) {
        this.cs = cs;
        this.isServer = isServer;
        try {
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);

        } catch (IOException e) {
            System.out.println("Error SocketClient(Socket cs)");
        }

        new Thread(() -> {
            run();
        }).start();
    }

    void run() {
        try {
            is = cs.getInputStream();
        } catch (IOException e) {
            System.out.println("Error run()");
        }
        dis = new DataInputStream(is);

        while (true) {
            if (isServer) {
                Message msg = readMessage();
                switch (msg.getAction()) {
                    case GET -> {
                        Response resp = new Response(model.getObjects());
                        sendResponse(resp);
                    }
                    case ADD -> {
                        for (Point pnt : msg.getObjects()) {
                            model.add(pnt);
                        }
                    }
                }
            } else {
                while (true) {
                    Response resp = readResponse();
                    model.set(resp.getObjects());
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
