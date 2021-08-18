package io.github.biielkts.servers.data;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.biielkts.Main;
import io.github.biielkts.servers.util.ServerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
@Setter
public class Server {
    private final String name, displayName, address;
    private ItemStack icon;
    private Boolean status;
    private Integer slots, playerCount, slot, port;

    public void refresh() {
        ServerStatus serverStatus = new ServerStatus(this.address, this.port);

        this.setStatus(serverStatus.isOnline());
        this.setPlayerCount(serverStatus.getPlayers());
    }

    public void send(Player player) {
        ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();

        byteArrayDataOutput.writeUTF("Connect");
        byteArrayDataOutput.writeUTF(name);

        player.sendPluginMessage(Main.getInstance(), "BungeeCord", byteArrayDataOutput.toByteArray());
    }

    public String getDisplayName() {
        return StringUtils.replaceEach(
                this.displayName,
                new String[]{
                        "Factions",
                        "Rankup",
                        "Full PvP",
                        "FullPvP"
                },
                new String[]{
                        "F.",
                        "R.",
                        "F. PVP",
                        "F. PVP"
                }
        );
    }

    public Boolean isOnline() { return this.status; }
}