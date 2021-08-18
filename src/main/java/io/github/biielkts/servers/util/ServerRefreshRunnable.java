package io.github.biielkts.servers.util;

import io.github.biielkts.servers.manager.ServerManager;

public class ServerRefreshRunnable implements Runnable {
    @Override
    public void run() {
        ServerManager.refresh();
    }
}
