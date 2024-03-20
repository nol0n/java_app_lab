package com.me.arrowgame;

import java.util.ArrayList;
import java.util.Map;

public class Response {
    ArrayList<Point> game_objects;

    public Response() {

    }

    public Response(ArrayList<Point> game_objects) {
        this.game_objects = game_objects;
    }

    public ArrayList<Point> getObjects() {
        return game_objects;
    }
}
