package com.me.arrowgame;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class _GameLoop {
    private final int UPDATE_RATE = 16;
    private final int FIRE_DELAY = 50;
    private final double ARROW_SPEED = 25;
    private long last_shoot_time = 0;

    private Thread gameThread = null;

    private long _ids = 0;
    private long _player_id = -1;
    private _Controller _controller;
    private _Game _game;
    private Map<Long, IGameObject> _game_objects = new ConcurrentHashMap<Long, IGameObject>();

    private _SignalCarrier signal_object = new _SignalCarrier();

    public void GameLoop() {}
    public void init(_Controller controller, _Game game) {
        _controller = controller;
        _game = game;
    }

    public void start() {
        if (_game.get_state() == _Game.STATE.PLAYING || _game.get_state() == _Game.STATE.PAUSE) { return; }

        create_target(530, 250, 20, 5, 250, 6, "#5aa7ee");
        create_target(430, 250, 40, 2.5, 250, 1, "#5aa7ee");

        create_target(300, 250, 10, 8, 250, -4, "#ed5a5a");
        create_target(270, 250, 10, 11, 250, -4, "#ed5a5a");

        _player_id = _ids;
        create_player(32, 250);

        _game.start();

        Runnable game_loop = new Runnable() {
            @Override
            public void run() {
                while(_game.get_state() != _Game.STATE.END) {
                    if (_game.get_state() == _Game.STATE.PAUSE) {
                        try {
                            signal_object.do_wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        Thread.sleep(UPDATE_RATE);
                    }
                    catch (InterruptedException ex) { }

                    update();
                    update_view();
                    check_collisions();
                }

                clear_field();
            }
        };

        gameThread = new Thread(game_loop);
        gameThread.start();
    }

    public void pause() {
        _game.switch_pause();
        if (_game.get_state() == _Game.STATE.PLAYING) {
            signal_object.do_notify();
        }
    }

    public void stop() {
        _game.reset();
        if (_game.get_state() == _Game.STATE.PAUSE) { signal_object.do_notify(); }
    }

    private void update() {
        for (Map.Entry<Long, IGameObject> game_obj : _game_objects.entrySet()) {
            game_obj.getValue().update();
        }
    }

    private void update_view() {
        for (Map.Entry<Long, IGameObject> game_obj : _game_objects.entrySet()) {
            IGameObject tmp = game_obj.getValue();
            _controller.update_view(tmp.get_id(), tmp.get_x(), tmp.get_y());
        }
    }

    private void check_collisions() {
        for (Map.Entry<Long, IGameObject> arrow_candidate : _game_objects.entrySet()) {
            if (arrow_candidate.getValue() instanceof _Arrow) {
                _Arrow arrow = ((_Arrow) arrow_candidate.getValue());
                // arrow hit limit
                if (arrow.get_x() > arrow.get_limit()) {
                    remove_object(arrow.get_id());
                }
                // arrow hit target
                else {
                    for (Map.Entry<Long, IGameObject> target_candidate : _game_objects.entrySet()) {
                        if (target_candidate.getValue() instanceof _Target) {
                            _Target target = ((_Target) target_candidate.getValue());
                            if (hit_target(arrow, target)) {
                                _game.set_score(_game.get_score() + target.get_reward());
                                remove_object(arrow.get_id());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void clear_field() {
        for (Map.Entry<Long, IGameObject> game_obj : _game_objects.entrySet()) {
            remove_object(game_obj.getValue().get_id());
        }
    }

    private void remove_object(long id) {
        _game_objects.remove(id);
        _controller.remove_object(id);
    }

    private void create_target(double x_coord, double y_coord, double radius, double speed, double limit, int reward, String color) {
        _game_objects.put(_ids, new _Target(_ids, x_coord, y_coord, radius, speed, limit, reward));
        _controller.add_circle(_ids, x_coord, y_coord, radius, color);
        _ids++;
    }

    private void create_player(double x_coord, double y_coord) {
        _game_objects.put(_ids, new _Player(_ids, x_coord, y_coord));
        _controller.add_polygon(_ids, _Player.points);
        _ids++;
    }

    private void create_arrow(double x_coord, double y_coord) {
        _game_objects.put(_ids, new _Arrow(_ids, x_coord, y_coord, ARROW_SPEED, 600));
        _controller.add_polygon(_ids, _Arrow.points);
        _ids++;
    }

    public void shoot() {
        if (System.currentTimeMillis() - last_shoot_time > FIRE_DELAY && _game.get_state() == _Game.STATE.PLAYING) {
            _game.set_shoots(_game.get_shoots() + 1);
            IGameObject player = _game_objects.get(_player_id);
            create_arrow(player.get_x() + 30, player.get_y());
            last_shoot_time = System.currentTimeMillis();
        }
    }

    private boolean hit_target(_Arrow arrow, _Target target) {
        double x1 = target.get_x(), x2 = arrow.get_x() + 25, y1 = target.get_y(), y2 = arrow.get_y();
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) <= target.get_radius();
    }

    public void change_score(int score) {
        _controller.change_score(score);
    }

    public void change_shoots(int shoots) {
        _controller.change_shoots(shoots);
    }

    public void change_player_position(double y) {
        if (50 < y && y < 450) {
            _Player tmp = (_Player) _game_objects.get(_player_id);
            tmp.set_y(y);
        }
    }
}
