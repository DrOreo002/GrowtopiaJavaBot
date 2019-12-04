package me.droreo002.bot.handler.model.tank;

import me.droreo002.bot.BotManager;
import me.droreo002.bot.handler.TankPacketHandler;
import me.droreo002.bot.utils.ByteReader;
import me.droreo002.bot.utils.PacketDataCollector;

import static me.droreo002.bot.utils.BotLog.*;
import static me.droreo002.bot.utils.PacketUtils.*;

public class OnSendToServerHandler implements TankPacketHandler {

    @Override
    public void handle(byte[] rawByte, ByteReader reader, PacketDataCollector collector) {
        String ip = (String) collector.getData(4).getData();
        ip = ip.substring(0, ip.length() - 1); // Remove last char
        int port = (int) collector.getData(1).getData();
        int userToken = (int) collector.getData(2).getData();
        int userId = (int) collector.getData(3).getData();

        // TODO: 03/12/2019 Change, debug purpose here
        try {
            BotManager.getBot(0).connect(ip, port, userId, userToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
