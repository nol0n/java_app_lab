package com.me.arrowgame;

import java.util.Random;
import java.lang.Math;

public class _Target implements IGameObject {
    private long _id;

    @Override
    public long get_id() {
        return _id;
    }

    private int _direction = -1;
    private double _start_y;
    private double _x_coord;

    private int _reward;

    public int get_reward() { return _reward; }

    @Override
    public double get_x() {
        return _x_coord;
    }

    private double _y_coord;

    @Override
    public double get_y() {
        return _y_coord;
    }

    private double _radius;
    private double _speed = 0.0;
    private double _limit = 0.0;


    public _Target(long id, double x_coord, double y_coord, double radius, double speed, double limit, int reward) {
        _id = id;
        _direction = generateRandomInt();
        _start_y = y_coord;

        _x_coord = x_coord;
        _y_coord = y_coord;
        _radius = radius;
        _speed = speed;
        _limit = limit;
        _reward = reward;
    }

    public double get_radius() {
        return _radius;
    }

    @Override
    public void update() {
        if (Math.abs(_start_y - _y_coord) + _speed + _radius > _limit) {
            _direction *= -1;
        }

        _y_coord += _speed * _direction;
    }

    public static int generateRandomInt() {
        Random random = new Random();
        return random.nextInt(2) == 0 ? -1 : 1;
    }
}
