package main.java.fr.verymc.spigot.core;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PluginMessageManager implements PluginMessageListener {

    public static PluginMessageManager instance;

    public PluginMessageManager() {
        instance = this;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        String data = in.readUTF();

        System.out.println(subchannel + " : " + data);

        if (subchannel.equals("inv")) {
            if (player.isOnline()) {
                InventorySyncManager.instance.setInventory(player, data);
            }
            return;
        }
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        if (subchannel.equals("subtp")) {
            try {
                json = (JSONObject) parser.parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Location location = ObjectConverter.instance.locationFromString(String.valueOf(json.get("coords")));
            String serverName = (String) json.get("serverName");
            if (!Main.instance.serverName.equals(serverName)) return;
            if (location == null) return;
            if (!location.getWorld().equals(Main.instance.mainWorld)) location.setWorld(Main.instance.mainWorld);
            player.teleport(location);
            return;
        }
    }

    //Usage PluginMessageManager.instance.sendMessage(player, "subtp", jsonObject.toJSONString(), "skyblock:toproxy");

    public void sendMessage(Player player, String subchannel, String data, String mainChannel) {

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subchannel);
        out.writeUTF(data);

        player.sendPluginMessage(Main.instance, mainChannel, out.toByteArray());
    }
}
