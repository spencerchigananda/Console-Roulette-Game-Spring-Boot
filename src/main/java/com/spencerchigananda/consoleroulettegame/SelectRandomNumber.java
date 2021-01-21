package com.spencerchigananda.consoleroulettegame;

import java.security.SecureRandom;
import java.util.TimerTask;
import java.util.function.Consumer;

public class SelectRandomNumber extends TimerTask {

    private Consumer<Integer> callback;

    public SelectRandomNumber() {
    }

    public SelectRandomNumber(Consumer<Integer> callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        // Select a random number between 0 and 36 (inclusive)
        SecureRandom random = new SecureRandom();
        callback.accept(random.nextInt(37)); // 0 to 36 inclusive

    }
}
