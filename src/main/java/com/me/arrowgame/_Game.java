package com.me.arrowgame;

public class _Game {
    private _GameLoop _game_loop;

    public _Game(_GameLoop game_loop) {
        _game_loop = game_loop;
    }
    private int _score;

    public int get_score() {
        return _score;
    }

    public void set_score(int score) {
        _score = score;
        _game_loop.change_score(_score);
    }
    private int _shoots;

    public int get_shoots() {
        return _shoots;
    }

    public void set_shoots(int shoots) {
        _shoots = shoots;
        _game_loop.change_shoots(_shoots);
    }

    public enum STATE {
        PLAYING,
        END,
        PAUSE
    }

    private STATE GAME_STATE;

    public STATE get_state() {
        return GAME_STATE;
    }

    public _Game() {
        _score = 0;
        _shoots = 0;
    }

    public void start() {
        GAME_STATE = STATE.PLAYING;
        set_score(0);
        set_shoots(0);
    }

    public void reset() {
        GAME_STATE = STATE.END;
        set_score(0);
        set_shoots(0);
    }

    public void switch_pause() {
        if (GAME_STATE == STATE.PAUSE) {
            GAME_STATE = STATE.PLAYING;
        } else if (GAME_STATE == STATE.PLAYING) {
            GAME_STATE = STATE.PAUSE;
        }
    }
}
