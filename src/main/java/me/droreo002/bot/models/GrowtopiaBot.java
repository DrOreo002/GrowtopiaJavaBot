package me.droreo002.bot.models;

import enetjava.objects.ENetEvent;
import enetjava.objects.ENetHost;
import enetjava.objects.ENetPacket;
import enetjava.objects.ENetPeer;
import lombok.Data;
import me.droreo002.bot.BotManager;
import me.droreo002.bot.enums.ActionPacket;
import me.droreo002.bot.handler.PacketHandler;
import me.droreo002.bot.enums.PacketType;
import me.droreo002.bot.logging.BotLog;
import me.droreo002.bot.utils.PacketUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    public GrowtopiaBot(BotData botData) {
        this.userName = botData.getBotUsername();
        this.userPassword = botData.getBotPassword();
        this.gameObjects = new ArrayList<>();
    }

    public void sendMessage(String msg) {
        PacketUtils.sendPacket(2, ActionPacket.INPUT_TEXT.asBuilder().append(msg).getPacketData(), peer);
    }

    public void sendCommand(String command) {
        sendMessage("/" + command);
    }

    /*
    Other shit
     */

    public void loop() {
        if (worldEnterDelay > 200 && !currentWorld.equals(queueWorld)) {
            if (queueWorld.equals("") || queueWorld.equals("-")) {
                worldEnterDelay = 0;
            } else {
                sendPacket(3, "action|join_request\nname|" + queueWorld, peer);
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

        BotLog.log("Bot with the username of " + userName + " has been connected!", BotLog.LogType.BOT);
    }

    public void eventLoop() throws Exception {
        ENetEvent event = null;
        ENetPacket packet = null;
        while (BotManager.isRunning()) {
            if ((event = client.startService(1000)) != null) {
                if (event.getType() == ENetEvent.Type.CONNECT) {
                    // TODO: 03/12/2019 Make something?
                }
                if (event.getType() == ENetEvent.Type.RECEIVE) {
                    packet = event.getPacket();
                    if (packet == null) continue;
                    byte[] b = getBytes(packet.getBytes());
                    PacketHandler handler = PacketUtils.determineHandler(b);
                    if (handler == null) {
                        PacketType.ON_UNKNOWN.getTankHandler().handle(b, null, null);
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
                    BotLog.log("Disconnected from the server..", BotLog.LogType.BOT);
                }
            }
        }
    }
}
