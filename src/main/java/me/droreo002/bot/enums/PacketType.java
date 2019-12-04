package me.droreo002.bot.enums;


import lombok.Getter;
import me.droreo002.bot.handler.PacketHandler;
import me.droreo002.bot.handler.TankPacketHandler;
import me.droreo002.bot.handler.model.OnLoginHandler;
import me.droreo002.bot.handler.model.OnPacketType3Handler;
import me.droreo002.bot.handler.model.OnPacketType4Handler;
import me.droreo002.bot.handler.model.OnPacketType6Handler;
import me.droreo002.bot.handler.model.tank.OnUnknownHandler;
import me.droreo002.bot.handler.model.tank.OnSendToServerHandler;

public enum PacketType {

    /*
    Actual packet
     */
    ON_LOGIN_REQUESTED("OnLoginRequested", 1),
    // Packet type 4
    ON_SEND_TO_SERVER("OnSendToServer"),
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
    ON_PACKET_TYPE_3("OnPacketType3", 3),
    ON_PACKET_TYPE_4("OnPacketType4", 4),
    ON_PACKET_TYPE_6("OnPacketType6", 6),
    ON_CONNECTED("OnConnected", -1),
    ON_DISCONNECTED("OnDisconnected", -1),
    ON_UNKNOWN("OnUnknown", -1);

    @Getter
    private String name;
    @Getter
    private int packetTypeId;

    PacketType(String name, int packetTypeId) {
        this.name = name;
        this.packetTypeId = packetTypeId;
    }

    PacketType(String name) {
        this.name = name;
        this.packetTypeId = -1;
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
        }
        return null;
    }


    public static PacketType fromPacketName(String name) {
        for (PacketType n : values()) {
            if (n.getName().equals(name)) return n;
        }
        return null;
    }


    public static PacketType fromPacketId(int packetId) {
        for (PacketType n : values()) {
            if (n.getPacketTypeId() == packetId) return n;
        }
        return null;
    }
}
