package main.java.fr.verymc.velocity;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ChannelsManager {

    public static ChannelsManager instance;

    private final ProxyServer server;
    private final Logger logger;

    public ChannelsManager(ProxyServer server, Logger logger) {
        instance = this;
        this.server = server;
        this.logger = logger;

        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("skyblock", "tospigot"));
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.from("skyblock:toproxy"));
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent e) {

        if(e.getIdentifier().getId().equals("skyblock:tospigot")) {
            e.setResult(PluginMessageEvent.ForwardResult.handled());
            return;
        }
        if(!e.getIdentifier().getId().equals("skyblock:toproxy")) return;
        e.setResult(PluginMessageEvent.ForwardResult.handled());

        if(!(e.getTarget() instanceof Player)) return;

        handleMessage((Player) e.getTarget(), e.getData());


    }

    public void handleMessage(Player player, byte[] data) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        try {
            String subchannel = in.readUTF();

            if (subchannel.equals("subtp")) {
                String rawData = in.readUTF();
                System.out.println("raw: " + rawData);
                sendPluginMessage(player, "subtp", null, rawData);
            }
        } catch (Exception e) {
            logger.error("Error while handling message", e);
        }
    }

    //Usage sendPluginMessage(player, "island", nameofserver);

    public void sendPluginMessage(Player player, String channel, String serverName, String... data) {
        if (player == null) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channel);
        for (String s : data) {
            out.writeUTF(s);
        }
        Optional<ServerConnection> serverOp = player.getCurrentServer();
        if (!serverOp.isPresent()) {
            throw new IllegalStateException("No server to send data to");
        }
        if (serverName == null) {
            serverOp.get().sendPluginMessage(MinecraftChannelIdentifier.from("skyblock:topigot"), out.toByteArray());
        }
        server.getScheduler()
                .buildTask(Main.instance, () -> {
                    player.getCurrentServer().get().sendPluginMessage(MinecraftChannelIdentifier.from("skyblock:tospigot"), out.toByteArray());
                })
                .delay(1L, TimeUnit.SECONDS)
                .schedule();
    }
}