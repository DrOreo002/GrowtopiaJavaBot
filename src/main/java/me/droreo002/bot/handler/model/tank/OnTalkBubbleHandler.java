package me.droreo002.bot.handler.model.tank;

import me.droreo002.bot.BotManager;
import me.droreo002.bot.command.BotCommandHandler;
import me.droreo002.bot.handler.TankPacketHandler;
import me.droreo002.bot.models.GrowtopiaBot;
import me.droreo002.bot.utils.ByteReader;
import me.droreo002.bot.utils.PacketDataCollector;

public class OnTalkBubbleHandler implements TankPacketHandler {

    @Override
    public void handle(byte[] packetData, ByteReader reader, PacketDataCollector collector) {
        String msg = (String) collector.getData(2).getData();
        int sender = (int) collector.getData(1).getData();
        for (GrowtopiaBot bot : BotManager.getBots()) {
            BotCommandHandler.handle(bot, msg);
        }
    }
}
