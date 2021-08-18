package io.github.biielkts.tablist;

import com.comphenix.protocol.reflect.FieldAccessException;
import io.github.biielkts.Main;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TablistManager {
    private static String header, footer;

    public TablistManager() {
        StringBuilder header = new StringBuilder();

        ConfigurationSection configurationSection = Main.getInstance().getConfig().getConfigurationSection("settings.tablist");

        configurationSection.getStringList("header").forEach(line -> header.append(line).append("\n"));

        TablistManager.header = StringUtils.replaceEach(
                header.toString(),
                new String[] {

                },
                new String[] {

                }
        );

        StringBuilder footer = new StringBuilder();

        configurationSection.getStringList("footer").forEach(line -> footer.append(line).append("\n"));

        TablistManager.footer = StringUtils.replaceEach(
                footer.toString(),
                new String[] {
                        "${shop_url}"
                },
                new String[] {
                        Main.getInstance().getConfig().getString("settings.shop_url")
                }
        );
    }

    public static void setTablist(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        PlayerConnection playerConnection = entityPlayer.playerConnection;

        IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{'text': '" + TablistManager.header + "'}");
        IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{'text': '" + TablistManager.footer + "'}");

        PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter(header);

        playerConnection.sendPacket(packetPlayOutPlayerListHeaderFooter);

        try {
            Field field = packetPlayOutPlayerListHeaderFooter.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packetPlayOutPlayerListHeaderFooter, footer);
        } catch (FieldAccessException | NoSuchFieldException
                | IllegalAccessException exception) {
            exception.printStackTrace();
        } finally {
            playerConnection.sendPacket(packetPlayOutPlayerListHeaderFooter);
        }
    }
}
