package io.github.biielkts.servers.util;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author SrGutyerrez
 **/
public class ServerStatus {
    protected StatusResponse response;

    public ServerStatus(String address, Integer port) {
        try {
            MinecraftServerPinger minecraftServerPinger = new MinecraftServerPinger(new InetSocketAddress(address, port));
            response = minecraftServerPinger.fetchData();
        } catch (InvalidResponseException | IOException ignored) {
        }
    }

    public Boolean isOnline() {
        return response != null;
    }

    public Integer getPlayers() {
        return (response == null ? 0 : response.getPlayerOnline());
    }
}
