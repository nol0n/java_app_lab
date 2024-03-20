package com.me.arrowgame;

public class _Arrow implements IGameObject {
    public static double[] points = {-50.0, -15.0, 0.0, 0.0, -50.0, 15.0};

    private long _id;

    @Override
    public long get_id() { return _id; }

    private double _x_coord;

    @Override
    public double get_x() { return _x_coord; }

    private double _y_coord;

    @Override
    public double get_y() { return _y_coord; }

    private double _speed;

    public double get_speed() { return _speed; }

    private double _limit;

    public double get_limit() {
        return _limit;
    }

    public _Arrow(long id, double x_coord, double y_coord, double speed, double limit) {
        _id = id;
        _x_coord = x_coord;
        _y_coord = y_coord;
        _speed = speed;
        _limit = limit;
    }
    @Override
    public void update() {
        _x_coord += _speed;
    }
}
