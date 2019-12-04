package me.droreo002.bot.handler.model;

import me.droreo002.bot.handler.PacketHandler;
import me.droreo002.bot.models.GrowtopiaBot;
import me.droreo002.bot.utils.BotLog;

import static me.droreo002.bot.utils.PacketUtils.*;

public class OnPacketType3Handler extends PacketHandler {

    @Override
    public void handle(byte[] packetData, int packetType, GrowtopiaBot bot) {
        BotLog.log("Found string: " + beautify(getTextFromPacket(packetData)));
    }
}
