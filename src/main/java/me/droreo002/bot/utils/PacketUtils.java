package me.droreo002.bot.utils;


import enetjava.objects.ENetPacket;
import enetjava.objects.ENetPeer;
import me.droreo002.bot.enums.DataType;
import me.droreo002.bot.handler.PacketHandler;
import me.droreo002.bot.enums.PacketType;
import me.droreo002.bot.handler.TankPacketHandler;
import me.droreo002.bot.logging.BotLog;

import java.nio.ByteBuffer;
import java.util.EnumSet;

import static me.droreo002.bot.utils.GamePacket.*;
import static me.droreo002.bot.logging.BotLog.log;

public class PacketUtils {

    public static void sendData(ENetPeer peer, byte[] num, byte[] data, int len) throws Exception {
        ByteBuffer buf = GamePacket.createDataPacket(num, data, len);
        ENetPacket packet = new ENetPacket(buf, EnumSet.of(ENetPacket.Flag.RELIABLE));
        peer.sendPacket(0, packet);
    }

    public static void sendConsoleMessage(ENetPeer peer, String message) throws Exception {
        ByteBuffer buf = packetEnd(appendString(appendString(createPacket(), "OnConsoleMessage"), message)).getData();
        ENetPacket packet = new ENetPacket(buf, EnumSet.of(ENetPacket.Flag.RELIABLE));
        peer.sendPacket(0, packet);
    }

    public static void sendPacket(int packetId, String packetData, ENetPeer peer) {
        if (peer == null) return;
        byte[] pData = new byte[packetData.length() + 5];
        pData[0] = (byte) packetId;
        System.arraycopy(packetData.getBytes(), 0, pData, 4, packetData.length());
        try {
            ENetPacket packet = new ENetPacket(pData, EnumSet.of(ENetPacket.Flag.RELIABLE));
            peer.sendPacket(0, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getBytes(ByteBuffer byteBuffer) {
        if (!byteBuffer.hasArray()) {
            byte[] data = new byte[byteBuffer.capacity()];
            byteBuffer.get(data);
            return data;
        }
        return byteBuffer.array();
    }

    public static String getHexDump(byte[] bytes) {
        char[] c = new char[bytes.length * 2];
        int b;
        for (int i = 0; i < bytes.length; i++) {
            b = bytes[i] >> 4;
            c[i * 2] = (char)(55 + b + (((b-10)>>31)&-7));
            b = bytes[i] & 0xF;
            c[i * 2 + 1] = (char)(55 + b + (((b-10)>>31)&-7));
        }
        return new String(c);
    }

    public static int getPacketId(byte[] packetData) {
        return packetData[0];
    }

    /**
     * Get the actual packet data
     * by removing its packet type id
     *
     * @param packetData The packet data
     * @return modified packet data as array of byte
     */
    public static byte[] getPacketData(byte[] packetData) {
        byte[] dat = new byte[packetData.length + 1];
        System.arraycopy(packetData, 4, dat, 0, packetData.length - 4);
        return dat;
    }

    public String textEncode(String text) {
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (text.charAt(i) != 0) {
            switch (text.charAt(i)) {
                case '\n':
                    ret.append("\\n");
                    break;
                case '\t':
                    ret.append("\\t");
                    break;
                case '\b':
                    ret.append("\\b");
                    break;
                case '\\':
                    ret.append("\\\\");
                    break;
                case '\r':
                    ret.append("\\r");
                    break;
                default:
                    ret.append(text.charAt(i));
                    break;
            }
            i++;
        }
        return ret.toString();
    }


    public static PacketHandler determineHandler(byte[] packetData) {
        int packetId = getPacketId(packetData);
        switch (packetId) {
            case 3:
                return PacketType.ON_PACKET_TYPE_3.getHandler();
            case 4:
                return PacketType.ON_PACKET_TYPE_4.getHandler();
            case 6:
                return PacketType.ON_PACKET_TYPE_6.getHandler();
            case 1:
                return PacketType.ON_LOGIN_REQUESTED.getHandler();
        }
        return null;
    }

    public static void print(byte[] packetData) {
        StringBuilder bytes = new StringBuilder();
        for (byte b : packetData.clone()) {
            bytes.append((b & 0xFF)).append(", ");
        }
        BotLog.log(bytes.toString(), BotLog.LogType.BOT);
    }

    public static String getTextFromPacket(byte[] packetData) {
        System.arraycopy(new byte[]{0x00}, 0, packetData, packetData.length - 1, 1);
        return new String(getPacketData(packetData));
    }

    public static String beautify(String packetData) {
        return packetData.replace("\n", " ");
    }

    public static String readableByte(byte[] packetData) {
        StringBuilder builder = new StringBuilder();
        for (byte b : packetData) {
            builder.append((b & 0xFF));
        }
        return builder.toString();
    }

    public static int getTankPacketId(byte[] packetData) {
        if (getPacketId(packetData) != 4) return -1;
        return packetData[4];
    }


    public static byte[] getStructPointerFromTankPacket(byte[] packetData) {
        int length = packetData.length;
        ByteBuffer byteBuffer = ByteBuffer.wrap(packetData);
        if (length >= 60) {
            if ((byteBuffer.get(16) & 8) != 0) {
                if (length < ((int)byteBuffer.get(56)) + 60) {
                    BotLog.log("[TankPacket] WARNING: Tank packet is to small for extended packet to be valid!", BotLog.LogType.TANK_PACKET);
                    return null;
                }
            } else {
                byteBuffer.put(56, (byte) 1);
            }
        }
        return getPacketData(byteBuffer.array());
    }

    public static int getExtendedDataFromTankPacket(byte[] packetData) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(packetData);
        return byteBuffer.getInt(56);
    }

    public static byte[] skipByte(byte[] bytes, int skip) {
        byte[] n = new byte[bytes.length - skip];
        System.arraycopy(bytes, skip, n, 0, n.length);
        return n;
    }

    public static void processTankPacket(byte[] packetData, int bufferSize, int netId) {
        ByteReader reader = new ByteReader(packetData);
        byte count = reader.readByte();

        log("----------------------> Processing tank packet!. Count: " + count + " Size: " + bufferSize + " NetID: " + netId, BotLog.LogType.TANK_PACKET);

        Process process = new Process();

        PacketDataCollector collector = new PacketDataCollector(count);
        for (int i = 0; i < count; i++) {
            byte index = reader.readByte();
            byte type = reader.readByte();
            DataType dataType = DataType.fromAlias(type);
            if (dataType == null) {
                log("Unknown data type... " + type, BotLog.LogType.TANK_PACKET);
                continue;
            }

            // Don't use switch, its messy
            if (dataType == DataType.STRING) {
                int strLength = reader.readByte() & 0xFF;
                reader.skip(3);
                String dat = new String(reader.read(strLength));
                collector.add(new PacketDataCollector.Data(dataType, index, dat));
            }

            if (dataType == DataType.INT) {
                int data = reader.readInt();
                collector.add(new PacketDataCollector.Data(dataType, index, data));
            }

            if (dataType == DataType.FLOAT) {
                log("Found float!", BotLog.LogType.TANK_PACKET);
                float dat = reader.readFloat();
            }

            if (dataType == DataType.VECTOR2) {
                float x = reader.readFloat();
                float y = reader.readFloat();
                log("Found Vector2! X:" + x + " Y:" + y, BotLog.LogType.TANK_PACKET);
            }

            if (dataType == DataType.VECTOR3) {
                float x = reader.readFloat();
                float y = reader.readFloat();
                float z = reader.readFloat();
                log("Found Vector3! X:" + x + " Y:" + y + " Z:" + z, BotLog.LogType.TANK_PACKET);
            }
        }

        log("Found packet: " + collector.toString(), BotLog.LogType.TANK_PACKET);

        PacketType packetType = PacketType.fromPacketName((String) collector.getData(DataType.STRING).get(0).getData());
        if (packetType == null) {
            PacketType.ON_UNKNOWN.getTankHandler().handle(packetData, reader, collector);
        } else {
            TankPacketHandler handler = packetType.getTankHandler();
            if (handler == null) {
                log("Failed to find tank packet handler for " + packetType.getName(), BotLog.LogType.TANK_PACKET);
            } else {
                handler.handle(packetData, reader, collector);
            }
        }

        log(process.stop("----------------------> Success!. took %timems!"), BotLog.LogType.TANK_PACKET);
    }

    public static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashInBytes.length; i++) {
            sb.append(Integer.toString((hashInBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
