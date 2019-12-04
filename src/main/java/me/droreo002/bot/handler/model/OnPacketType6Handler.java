package me.droreo002.bot.handler.model;

import me.droreo002.bot.handler.PacketHandler;
import me.droreo002.bot.models.GrowtopiaBot;

import static me.droreo002.bot.utils.BotLog.log;
import static me.droreo002.bot.utils.PacketUtils.getHexDump;
import static me.droreo002.bot.utils.PacketUtils.sendPacket;

public class OnPacketType6Handler extends PacketHandler {

    @Override
    public void handle(byte[] packetData, int packetType, GrowtopiaBot bot) {
        //log("Handling packet type " + packetType + " packet is " + getHexDump(packetData));
        sendPacket(new byte[]{0x02}, "action|enter_game\n", bot.getPeer());
        bot.getClient().flush();
    }
}
