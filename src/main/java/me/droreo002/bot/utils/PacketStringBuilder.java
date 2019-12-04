package me.droreo002.bot.utils;

public class PacketStringBuilder {

    private StringBuilder packetData;

    private PacketStringBuilder(StringBuilder packetData) {
        packetData.append("\n");
        this.packetData = packetData;
    }

    public static PacketStringBuilder init() {
        return new PacketStringBuilder(new StringBuilder());
    }

    public PacketStringBuilder add(String key, Object val) {
        packetData.append(key).append("|").append(val).append("\n");
        return this;
    }

    public PacketStringBuilder append(String s) {
        packetData.append(s);
        return this;
    }

    public String getPacketData() {
        return packetData.toString();
    }
}
