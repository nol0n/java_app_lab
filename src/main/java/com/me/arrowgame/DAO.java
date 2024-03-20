package com.me.arrowgame;

import com.me.arrowgame.IGameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DAO implements Iterable<Point> {
    ArrayList<Point> game_objects = new ArrayList<>();

    void add(Point object) {
        game_objects.add(object);
    }

    public ArrayList<Point> getObjects() {
        return game_objects;
    }

    void set(ArrayList<Point> game_objects) {
        this.game_objects.clear();
        this.game_objects = game_objects;
    }

    /*void remove(Point pnt) {
        game_objects.remove(pnt);
    }*/

    @Override
    public Iterator<Point> iterator() {
        return game_objects.iterator();
    }
}
