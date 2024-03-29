package main.java.fr.verymc.spigot.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import main.java.fr.verymc.spigot.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WorldBorderUtils {


    public static WorldBorderUtils instanceClass;
    private Object worldBorder;
    private Method setCenter, setSize, setWarningTime, setWarningDistance, transistionSizeBetween;
    private Constructor<?> packetPlayOutWorldBorder;
    private Class<?> craftWorldClass;
    private int versionNumber;
    private String minecraftVersion;
    private JavaPlugin instance;

    public WorldBorderUtils(JavaPlugin instance) {
        try {
            this.instance = instance;
            this.instanceClass = this;
            /*minecraftVersion = getVersion();
            versionNumber = getVersionNumber();
            worldBorder = getNMSClass("WorldBorder").getConstructor().newInstance();
            setCenter = worldBorder.getClass().getMethod("setCenter", double.class, double.class);
            setSize = worldBorder.getClass().getMethod("setSize", double.class);
            setWarningTime = worldBorder.getClass().getMethod("setWarningTime", int.class);
            setWarningDistance = worldBorder.getClass().getMethod("setWarningDistance", int.class);
            transistionSizeBetween = worldBorder.getClass().getMethod("transitionSizeBetween", double.class, double.class, long.class);
            packetPlayOutWorldBorder = getNMSClass("PacketPlayOutWorldBorder").getConstructor(getNMSClass("WorldBorder"),
                    getNMSClass("PacketPlayOutWorldBorder").getDeclaredClasses()[getVersionNumber() > 100 ? 0 : 1]);
            craftWorldClass = getCraftClass("CraftWorld");*/


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendWorldBorder(Player player, double size, Location centerLocation) {
        try {
            /*PacketContainer packet1 = Main.instance.manager.createPacket(PacketType.Play.Server.INITIALIZE_BORDER);
            PacketContainer packet2 = Main.instance.manager.createPacket(PacketType.Play.Server.);
            packet1.getWorldBorderActions().writeSafely(0, EnumWrappers.WorldBorderAction.SET_CENTER);
            packet2.getWorldBorderActions().writeSafely(0, EnumWrappers.WorldBorderAction.SET_SIZE);
            packet1.getDoubles().write(0, centerLocation.getX());
            packet1.getDoubles().write(1, centerLocation.getZ());
            packet2.getDoubles().write(0, size);
            packet2.getDoubles().write(1, size);*/
            PacketContainer packet1 = Main.instance.manager.createPacket(PacketType.Play.Server.SET_BORDER_CENTER);
            packet1.getDoubles().write(0, centerLocation.getX());
            packet1.getDoubles().write(1, centerLocation.getZ());
            PacketContainer packet2 = Main.instance.manager.createPacket(PacketType.Play.Server.SET_BORDER_SIZE);
            packet2.getDoubles().write(0, size);


            try {
                Main.instance.manager.sendServerPacket(player, packet1);
                Main.instance.manager.sendServerPacket(player, packet2);
                /*Main.instance.manager.sendServerPacket(player, packet1);
                Main.instance.manager.sendServerPacket(player, packet2);*/
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /*Object craftWorld = craftWorldClass.cast(centerLocation.getWorld());
            setField(worldBorder, "world", craftWorld.getClass().getMethod("getHandle").invoke(craftWorld), false);

            setCenter.invoke(worldBorder, centerLocation.getBlockX(), centerLocation.getBlockZ());

            setSize.invoke(worldBorder, size);

            setWarningTime.invoke(worldBorder, 0);
            setWarningDistance.invoke(worldBorder, 0);

            switch (color) {
                case RED:
                    transistionSizeBetween.invoke(worldBorder, size, size - 1.0D, 20000000L);
                    break;
                case GREEN:
                    transistionSizeBetween.invoke(worldBorder, size - 0.1D, size, 20000000L);
                    break;
                case BLUE:
                default:
                    // Do nothing as border is blue, default blue
            }

            Object packetPlayOutWorldBorderPacket = packetPlayOutWorldBorder.newInstance(worldBorder,
                    Enum.valueOf((Class<Enum>) getNMSClass("PacketPlayOutWorldBorder").getDeclaredClasses()[versionNumber > 100 ? 0 : 1],
                            "INITIALIZE"));
            sendPacket(player, packetPlayOutWorldBorderPacket);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + minecraftVersion + "." + name);
    }

    public Class<?> getCraftClass(String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + name);
    }

    public void setField(Object object, String fieldName, Object fieldValue, boolean declared) {
        try {
            Field field;

            if (declared) {
                field = object.getClass().getDeclaredField(fieldName);
            } else {
                field = object.getClass().getField(fieldName);
            }

            field.setAccessible(true);
            field.set(object, fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getVersion() {
        return instance.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public int getVersionNumber() {
        return Integer.parseInt(minecraftVersion.substring(1, getVersion().length() - 3).replace("_", ""));
    }

    public String borderToString(WorldBorderUtils.Color color) {
        switch (color) {
            case RED:
                return "RED";
            case GREEN:
                return "GREEN";
            default:
                return "BLUE";
        }
    }

    public WorldBorderUtils.Color borderFromString(String str) {
        switch (str) {
            case "RED":
                return WorldBorderUtils.Color.RED;
            case "GREEN":
                return WorldBorderUtils.Color.GREEN;
            default:
                return WorldBorderUtils.Color.BLUE;
        }
    }

    public enum Color {
        BLUE, GREEN, RED
    }
}
