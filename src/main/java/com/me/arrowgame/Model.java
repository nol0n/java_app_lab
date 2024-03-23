package com.me.arrowgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Model implements Iterable<Point> {
    DAO dao = new DAO();

    Map<Integer, IObserver> allObservers = new HashMap<Integer, IObserver>();

    void event() {
        for (IObserver obs : allObservers.values()) {
            obs.event(this);
        }
    }

    public void add(Point object) {
        dao.add(object);
        event();
    }

    public ArrayList<Point> getObjects() {
        return dao.getObjects();
    }

    void set(ArrayList<Point> game_objects) {
        dao.set(game_objects);
        event();
    }

    /*public void remove(int id) {
        dao.remove(id);
        event();
    }*/

    public void addObserver(int id, IObserver obs) {
        allObservers.put(id, obs);
    }

    public void removeObserver(int id) {
        allObservers.remove(id);
    }

    @Override
    public Iterator<Point> iterator() {
        return dao.iterator();
    }
}
