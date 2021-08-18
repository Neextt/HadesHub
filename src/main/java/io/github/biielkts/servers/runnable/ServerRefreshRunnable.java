package io.github.biielkts.servers.runnable;

import io.github.biielkts.servers.manager.ServerManager;

public class ServerRefreshRunnable implements Runnable {
    @Override
    public void run() {
        ServerManager.refresh();
    }
}
