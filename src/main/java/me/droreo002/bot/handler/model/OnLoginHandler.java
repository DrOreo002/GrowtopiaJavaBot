package me.droreo002.bot.handler.model;

import me.droreo002.bot.handler.PacketHandler;
import me.droreo002.bot.models.GrowtopiaBot;
import me.droreo002.bot.logging.BotLog;
import me.droreo002.bot.utils.PacketStringBuilder;
import me.droreo002.bot.utils.PacketUtils;

import java.util.concurrent.ThreadLocalRandom;

public class OnLoginHandler implements PacketHandler {

    @Override
    public void handle(byte[] packetData, int packetType, GrowtopiaBot bot) {
        BotLog.log("Sending login packet..", BotLog.LogType.BOT);
        PacketStringBuilder builder = PacketStringBuilder.init()
                .add("tankIDName", bot.getUserName())
                .add("tankIDPass", bot.getUserPassword())
                .add("requestedName", "SmileZero")
                .add("f", 1)
                .add("protocol", 84)
                .add("game_version", "2.999")
                .add("fz", 5367464)
                .add("lmode", 0)
                .add("cbits", 0)
                .add("player_age", 18)
                .add("GDPR", 1)
                .add("hash2", random4Digit())
                .add("meta", generateMeta())
                .add("fhash", -716928004)
                .add("rid", generateRid())
                .add("platformID", 0)
                .add("deviceVersion", 0)
                .add("country", "us")
                .add("hash", random4Digit())
                .add("mac", generateMac())
                .add("wk", generateRid())
                .add("zf", -496303939);

        if (bot.getUserId() != 0) builder.add("user", bot.getUserId());
        if (bot.getUserToken() != 0) builder.add("token", bot.getUserToken());

        PacketUtils.sendPacket(2, builder.getPacketData(), bot.getPeer());
    }

    private String random4Digit() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            b.append(ThreadLocalRandom.current().nextInt());
        }
        return b.toString();
    }

    private String generateRid() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            b.append(Integer.toHexString(ThreadLocalRandom.current().nextInt()));
        }
        return b.toString().toUpperCase();
    }

    private String generateMeta() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            b.append(Integer.toHexString(ThreadLocalRandom.current().nextInt()));
        }
        return b.append(".com").toString();
    }

    private String generateMac() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            b.append(Integer.toHexString(ThreadLocalRandom.current().nextInt()));
            if (i != 5)
                b.append(":");
        }
        return b.toString();
    }
}
