package me.droreo002.bot.handler.model.tank;

import me.droreo002.bot.handler.TankPacketHandler;
import me.droreo002.bot.logging.BotLog;
import me.droreo002.bot.utils.ByteReader;
import me.droreo002.bot.utils.PacketDataCollector;
import me.droreo002.bot.utils.PacketUtils;

public class OnUnknownHandler implements TankPacketHandler {

    @Override
    public void handle(byte[] rawByte, ByteReader reader, PacketDataCollector collector) {
        BotLog.log("Failed to process unknown packet [" + PacketUtils.getHexDump(rawByte) + "]", BotLog.LogType.PACKET);
    }
}
