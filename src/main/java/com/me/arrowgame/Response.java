package com.me.arrowgame;

import java.util.ArrayList;
import java.util.Map;

public class Response {
    ArrayList<Point> game_objects;

    String message;

    responseAction action;
    public Response() {

    }

    public Response(ArrayList<Point> game_objects, String message, responseAction action) {
        this.message = message;
        this.game_objects = game_objects;
        this.action = action;
    }

    public responseAction getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Point> getObjects() {
        return game_objects;
    }
}
