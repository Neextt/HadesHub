package io.github.biielkts.tagger.packets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

class PacketAccessor {

    static Field MEMBERS;
    static Field PREFIX;
    static Field SUFFIX;
    static Field TEAM_NAME;
    static Field PARAM_INT;
    static Field PACK_OPTION;
    static Field DISPLAY_NAME;
    static Field PUSH;
    static Field VISIBILITY;
    private static boolean CAULDRON_SERVER;
    private static Method getHandle;
    private static Method sendPacket;
    private static Field playerConnection;
    private static Class<?> packetClass;

    static {
        PacketAccessor.CAULDRON_SERVER = false;
        try {
            Class.forName("cpw.mods.fml.common.Mod");
            PacketAccessor.CAULDRON_SERVER = true;
        } catch (ClassNotFoundException ex) {
        }
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            Class<?> typeCraftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            PacketAccessor.getHandle = typeCraftPlayer.getMethod("getHandle", new Class[0]);
            if (PacketAccessor.CAULDRON_SERVER) {
                PacketAccessor.packetClass = Class.forName("net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardTeam");
                Class<?> typeNMSPlayer = Class.forName("net.minecraft.server.v1_7_R4.EntityPlayer");
                Class<?> typePlayerConnection = Class.forName("net.minecraft.server.v1_7_R4.PlayerConnection");
                PacketAccessor.playerConnection = typeNMSPlayer.getField("field_71135_a");
                PacketAccessor.sendPacket = typePlayerConnection.getMethod("func_147359_a",
                        Class.forName("net.minecraft.server.v1_7_R4.Packet"));
            } else {
                PacketAccessor.packetClass = Class
                        .forName("net.minecraft.server." + version + ".PacketPlayOutScoreboardTeam");
                Class<?> typeNMSPlayer = Class.forName("net.minecraft.server." + version + ".EntityPlayer");
                Class<?> typePlayerConnection = Class.forName("net.minecraft.server." + version + ".PlayerConnection");
                PacketAccessor.playerConnection = typeNMSPlayer.getField("playerConnection");
                PacketAccessor.sendPacket = typePlayerConnection.getMethod("sendPacket",
                        Class.forName("net.minecraft.server." + version + ".Packet"));
            }
            PacketData currentVersion = null;
            for (PacketData packetData : PacketData.values()) {
                if (version.contains(packetData.name())) {
                    currentVersion = packetData;
                }
            }
            if (PacketAccessor.CAULDRON_SERVER) {
                currentVersion = PacketData.cauldron;
            }
            if (currentVersion != null) {
                PacketAccessor.PREFIX = getNMS(currentVersion.getPrefix());
                PacketAccessor.SUFFIX = getNMS(currentVersion.getSuffix());
                PacketAccessor.MEMBERS = getNMS(currentVersion.getMembers());
                PacketAccessor.TEAM_NAME = getNMS(currentVersion.getTeamName());
                PacketAccessor.PARAM_INT = getNMS(currentVersion.getParamInt());
                PacketAccessor.PACK_OPTION = getNMS(currentVersion.getPackOption());
                PacketAccessor.DISPLAY_NAME = getNMS(currentVersion.getDisplayName());
                if (isPushVersion(version)) {
                    PacketAccessor.PUSH = getNMS(currentVersion.getPush());
                }
                if (isVisibilityVersion(version)) {
                    PacketAccessor.VISIBILITY = getNMS(currentVersion.getVisibility());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isPushVersion(String version) {
        return Integer.parseInt(version.split("_")[1]) >= 9;
    }

    private static boolean isVisibilityVersion(String version) {
        return Integer.parseInt(version.split("_")[1]) >= 8;
    }

    private static Field getNMS(String path) throws Exception {
        Field field = PacketAccessor.packetClass.getDeclaredField(path);
        field.setAccessible(true);
        return field;
    }

    static Object createPacket() {
        try {
            return PacketAccessor.packetClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static void sendPacket(Collection<? extends Player> players, Object packet) {
        for (Player player : players) {
            sendPacket(player, packet);
        }
    }

    static void sendPacket(Player player, Object packet) {
        try {
            Object nmsPlayer = PacketAccessor.getHandle.invoke(player);
            Object connection = PacketAccessor.playerConnection.get(nmsPlayer);
            PacketAccessor.sendPacket.invoke(connection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
