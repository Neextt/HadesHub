package io.github.biielkts.scoreboard.runnable;

import io.github.biielkts.scoreboard.manager.ScoreboardManager;

public class ScoreboardRefreshRunnable implements Runnable {
    @Override
    public void run() {
        ScoreboardManager.refresh();
    }
}
