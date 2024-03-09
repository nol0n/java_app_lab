package com.me.arrowgame;

public class SignalCarrier {
    private boolean _signal_raised  = false;

    public void do_wait() throws InterruptedException {
        synchronized (this) {
            while (!_signal_raised) {
                this.wait();
            }
            _signal_raised = false;
        }
    }

    public void do_notify() {
        synchronized (this) {
            _signal_raised = true;
            this.notify();
        }
    }
}
