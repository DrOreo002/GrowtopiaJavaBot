package me.droreo002.bot.handler.model;

import at.favre.lib.bytes.Bytes;
import me.droreo002.bot.handler.PacketHandler;
import me.droreo002.bot.models.GrowtopiaBot;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static me.droreo002.bot.utils.PacketUtils.*;
import static me.droreo002.bot.utils.BotLog.*;
public class OnPacketType4Handler extends PacketHandler {

    @Override
    public void handle(byte[] packetData, int packetType, GrowtopiaBot bot) {
        byte[] handled = getStructPointerFromTankPacket(packetData);
        if (handled != null) {
            int id = getTankPacketId(packetData);
            log("Found valid tank packet with id of " + id);
            switch (id) {
                case 1:
                    // Get the extended data and skip the bytes by 56
                    ByteBuffer byteBuffer = ByteBuffer.wrap(handled);
                    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                    processTankPacket(skipByte(handled, 56), byteBuffer.getInt(52), byteBuffer.getInt(4));
                    break;
                case 0x15:
                    log("Found ping reply!");
                    break;
                case 0x10:
                    log("Some items thing here...");
                    break;
                case 0xF:
                case 8:
                    log("Some packet tile change request here...");
                    break;
                case 9:
                    log("Update player inventory...");
                    break;
                case 5:
                    log("Updating block visual...");
                    break;
                case 0x16:
                    log("We need to send packet raw response!");
                    break;
                case 0x12:
                    log("AtAvatarSetIconState packet found");
                    break;
                case 0x14:
                    log("Set character state!");
                    break;
                case 0xC:
                    log("Tile change packet found!");
                    break;
                case 0xE:
                    log("Object change request!");
                    break;
                case 3:
                    log("Packet for destroy tile found!");
                    break;
                case 4:
                    log("Found world packet..");
                    print(handled);
                    break;
                case 0:
                    log("Player moving packet!");
                    print(handled);
                    break;
            }
        }
    }
}
