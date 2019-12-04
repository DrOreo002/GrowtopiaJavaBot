package me.droreo002.bot.handler;

import me.droreo002.bot.logging.BotLog;
import me.droreo002.bot.utils.ByteReader;
import me.droreo002.bot.utils.PacketDataCollector;
import me.droreo002.bot.utils.PacketUtils;

public interface TankPacketHandler {
    void handle(byte[] packetData, ByteReader reader, PacketDataCollector collector);

    default void handle(byte[] packetData) {
        BotLog.log("Failed to process unknown packet [" + PacketUtils.getHexDump(packetData) + "]");
    }
}
