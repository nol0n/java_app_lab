package com.me.arrowgame;

public class _Player implements IGameObject {
    public static double[] points = {-20.0, 40.0, 20.0, 0.0, -20.0, -40.0};
    private long _id;
    @Override
    public long get_id() {
        return _id;
    }

    private double _x_coord;
    @Override
    public double get_x() {
        return _x_coord;
    }

    private double _y_coord;
    @Override
    public double get_y() {
        return _y_coord;
    }

    public void set_y(double y_coord) {
        _y_coord = y_coord;
    }

    public _Player(long id, double x_coord, double y_coord) {
        _id = id;
        _x_coord = x_coord;
        _y_coord = y_coord;
    }

    @Override
    public void update() {

    }
}
