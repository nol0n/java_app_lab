package com.me.arrowgame;

import java.util.ArrayList;
import java.util.Map;

public class Message {
    ArrayList<Point> game_objects;

    messageAction action;

    public Message(ArrayList<Point> game_objects, messageAction action) {
        this.game_objects = game_objects;
        this.action = action;
    }

    public ArrayList<Point> getObjects() {
        return this.game_objects;
    }

    public messageAction getAction() {
        return this.action;
    }

    @Override
    public String toString() {
        return "message{" +
                "game_objects=" + game_objects +
                ", action=" + action +
                '}';
    }
}
