package com.me.arrowgame;

import java.util.ArrayList;
import java.util.Iterator;

public class Model implements Iterable<Point> {
    DAO dao = new DAO();

    ArrayList<IObserver> allObservers = new ArrayList<>();

    void event() {
        for (IObserver obs : allObservers) {
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

/*    public void remove(int id) {
        dao.remove(id);
        event();
    }*/

    public void addObserver(IObserver obs) {
        allObservers.add(obs);
    }

    @Override
    public Iterator<Point> iterator() {
        return dao.iterator();
    }
}
