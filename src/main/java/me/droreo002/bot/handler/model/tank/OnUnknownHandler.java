package me.droreo002.bot.handler.model.tank;

import me.droreo002.bot.handler.TankPacketHandler;
import me.droreo002.bot.utils.ByteReader;
import me.droreo002.bot.utils.PacketDataCollector;

public class OnUnknownHandler implements TankPacketHandler {

    @Override
    public void handle(byte[] rawByte, ByteReader reader, PacketDataCollector collector) {

    }
}
