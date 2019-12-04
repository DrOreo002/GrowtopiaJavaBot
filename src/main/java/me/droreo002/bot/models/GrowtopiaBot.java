package me.droreo002.bot.models;

import enetjava.objects.ENetEvent;
import enetjava.objects.ENetHost;
import enetjava.objects.ENetPacket;
import enetjava.objects.ENetPeer;
import lombok.Data;
import me.droreo002.bot.handler.PacketHandler;
import me.droreo002.bot.enums.PacketType;
import me.droreo002.bot.utils.BotLog;
import me.droreo002.bot.utils.PacketUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import static me.droreo002.bot.utils.PacketUtils.*;

@Data
public class GrowtopiaBot {

    private final String userName;
    private final String userPassword;

    private ENetPeer peer;
    private ENetHost client;
    private int userId;
    private int userToken;
    private String currentWorld;
    private String queueWorld;
    private int worldEnterDelay;
    private int owner;
    private List<GameObject> gameObjects;
    private boolean publicBot;
    private boolean following;
    private boolean backwardWalk;
    private int respawnX;
    private int respawnY;

    public GrowtopiaBot(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.gameObjects = new ArrayList<>();
    }

    public void loop() {
        if (worldEnterDelay > 200 && !currentWorld.equals(queueWorld)) {
            if (queueWorld.equals("") || queueWorld.equals("-")) {
                worldEnterDelay = 0;
            } else {
                sendPacket(new byte[]{0x03}, "action|join_request\nname|" + queueWorld, peer);
                gameObjects.clear();
            }
            worldEnterDelay = 0;
            currentWorld = queueWorld;
        }
        worldEnterDelay++;
    }

    public void connect(String address, int port, int userId, int userToken) throws Exception {
        this.userId = userId;
        this.userToken = userToken;

        this.client = new ENetHost(null, 1, 2, 0, 0);
        client.setUseCrc32(true);
        client.setCompressWithRangeCoder(true);
        this.peer = client.connectPeer(new InetSocketAddress(address, port), 2, 0);
        client.flush();

        BotLog.log("Bot with the username of " + userName + " has been connected!");
    }

    public void eventLoop() throws Exception {
        ENetEvent event = null;
        ENetPacket packet = null;
        while (true) {
            if ((event = client.startService(0)) != null) {
                if (event.getType() == ENetEvent.Type.CONNECT) {
                    // TODO: 03/12/2019 Make something?
                }
                if (event.getType() == ENetEvent.Type.RECEIVE) {
                    packet = event.getPacket();
                    if (packet == null) continue;
                    byte[] b = getBytes(packet.getBytes());
                    PacketHandler handler = PacketUtils.determineHandler(b);
                    if (handler == null) {
                        PacketType.ON_UNKNOWN.getTankHandler().handle(b);
                    } else {
                        handler.handle(b, getPacketId(b), this);
                    }
                    try {
                        packet.destroy();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                if (event.getType() == ENetEvent.Type.DISCONNECT) {
                    BotLog.log("Disconnected from the server..");
                }
            }
        }
    }
}
