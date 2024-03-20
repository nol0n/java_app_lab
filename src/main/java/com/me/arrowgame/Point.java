package com.me.arrowgame;

public class Point /*implements IGameObject*/ {

    public Point (long id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    private long id;
    // @Override
    public long get_id() {
        return this.id;
    }

    private double x;
    // @Override
    public double get_x() {
        return this.x;
    }

    private double y;
    // @Override
    public double get_y() {
        return this.y;
    }

    // @Override
    public void update() {

    }
}
