package me.droreo002.bot.handler;

import lombok.Getter;
import lombok.Setter;
import me.droreo002.bot.models.GrowtopiaBot;

public interface PacketHandler {
    void handle(byte[] packetData, int packetType, GrowtopiaBot bot);
}
