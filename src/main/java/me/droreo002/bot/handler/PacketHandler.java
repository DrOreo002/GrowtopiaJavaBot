package me.droreo002.bot.handler;

import lombok.Getter;
import lombok.Setter;
import me.droreo002.bot.models.GrowtopiaBot;

public abstract class PacketHandler {

    @Getter @Setter
    private byte[] lastPacket;
    @Getter @Setter
    private int lastPacketType;

    public abstract void handle(byte[] packetData, int packetType, GrowtopiaBot bot);
}
