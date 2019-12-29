package me.droreo002.bot.enums;


import lombok.Getter;
import me.droreo002.bot.handler.PacketHandler;
import me.droreo002.bot.handler.TankPacketHandler;
import me.droreo002.bot.handler.model.OnLoginHandler;
import me.droreo002.bot.handler.model.OnPacketType3Handler;
import me.droreo002.bot.handler.model.OnPacketType4Handler;
import me.droreo002.bot.handler.model.OnPacketType6Handler;
import me.droreo002.bot.handler.model.tank.OnTalkBubbleHandler;
import me.droreo002.bot.handler.model.tank.OnUnknownHandler;
import me.droreo002.bot.handler.model.tank.OnSendToServerHandler;

import java.util.ArrayList;
import java.util.List;

public enum PacketType {

    /*
    Actual packet
     */
    ON_LOGIN_REQUESTED("OnLoginRequested", 1),

    // Packet type 4
    ON_SEND_TO_SERVER("OnSendToServer", Integer.class, Integer.class, String.class, Integer.class),
    ON_CONSOLE_MESSAGE("OnConsoleMessage"),
    ON_PLAY_POSITIONED("OnPlayPositioned"),
    ON_SET_FREEZE_STATE("OnSetFreezeState"),
    ON_REMOVE("OnRemove"),
    ON_SPAWN("OnSpawn"),
    ON_ACTION("OnAction"),
    SET_HAS_GROWID("SetHasGrowID"),
    SET_HAS_ACCOUNT_SECURED("SetHasAccountSecured"),
    ON_TALK_BUBBLE("OnTalkBubble"),
    SET_RESPAWN_POS("SetRespawnPos"),
    ON_EMOTICON_DATA_CHANGED("OnEmoticonDataChanged"),
    ON_SET_POS("OnSetPos"),
    ON_ADD_NOTIFICATION("OnAddNotification"),
    AT_APPLY_TILE_DAMAGE("AtApplyTileDamage"),
    AT_APPLY_LOCK("AtApplyLock"),
    AT_AVATAR_SET_ICON_STATE("AtAvatarSetIconState"),

    /*
    Misc
     */
    ON_PACKET_TYPE_3("OnPacketType3", -1),
    ON_PACKET_TYPE_4("OnPacketType4", -1),
    ON_PACKET_TYPE_6("OnPacketType6", -1),
    ON_CONNECTED("OnConnected", -1),
    ON_DISCONNECTED("OnDisconnected", -1),
    ON_UNKNOWN("OnUnknown", -1);

    @Getter
    private String name;
    @Getter
    private int packetTypeId;
    @Getter
    private Class[] packetParam;

    PacketType(String name, int packetTypeId, Class... packetParam) {
        this.name = name;
        this.packetTypeId = packetTypeId;
        this.packetParam = packetParam;
    }

    PacketType(String name, Class... packetParam) {
        this.name = name;
        this.packetTypeId = 4;
        this.packetParam = packetParam;
    }

    PacketType(String name, int packetTypeId) {
        this.name = name;
        this.packetTypeId = packetTypeId;
        this.packetParam = null;
    }

    PacketType(String name) {
        this.name = name;
        this.packetTypeId = 4;
        this.packetParam = null;
    }

    public boolean isTankPacket() {
        return this.packetTypeId == 4;
    }

    public PacketHandler getHandler() {
        switch (name) {
            case "OnLoginRequested":
                return new OnLoginHandler();
            case "OnPacketType3":
                return new OnPacketType3Handler();
            case "OnPacketType4":
                return new OnPacketType4Handler();
            case "OnPacketType6":
                return new OnPacketType6Handler();
        }
        return null;
    }

    public TankPacketHandler getTankHandler() {
        switch (name) {
            case "OnSendToServer":
                return new OnSendToServerHandler();
            case "OnUnknown":
                return new OnUnknownHandler();
            case "OnTalkBubble":
                return new OnTalkBubbleHandler();
        }
        return null;
    }

    public static PacketType fromPacketName(String name) {
        for (PacketType n : values()) {
            if (n.getName().equals(name)) return n;
        }
        return null;
    }

    public static PacketType[] fromPacketId(int packetId) {
        List<PacketType> found = new ArrayList<>();
        for (PacketType n : values()) {
            if (n.getPacketTypeId() == packetId) found.add(n);
        }
        return (PacketType[]) found.toArray();
    }
}
