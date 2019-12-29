package me.droreo002.bot.packet;

import enetjava.objects.ENetPacket;
import enetjava.objects.ENetPeer;
import me.droreo002.bot.enums.PacketType;
import me.droreo002.bot.utils.GamePacket;

import java.util.EnumSet;

public interface GrowtopiaPacket {
    GamePacket buildPacket();
    PacketType getPacketType();
    default void sendPacket(ENetPeer eNetPeer) throws Exception {
        eNetPeer.sendPacket(0, new ENetPacket(buildPacket().getData(), EnumSet.of(ENetPacket.Flag.RELIABLE)));
    }
}
