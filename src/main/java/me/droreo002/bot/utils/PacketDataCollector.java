package me.droreo002.bot.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import me.droreo002.bot.enums.DataType;
import me.droreo002.bot.enums.PacketType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacketDataCollector {

    @Getter
    private List<Data> dataObjects;
    @Getter
    private int packetCount;
    @Getter
    private StringBuilder mappings;

    public PacketDataCollector(int packetCount) {
        this.dataObjects = new ArrayList<>();
        this.packetCount = packetCount;
        this.mappings = new StringBuilder();
    }

    public void add(Data data) {
        mappings.append(data.dataType.name())
                .append("[")
                .append(data.index)
                .append("]=")
                .append(data.data)
                .append(" ");

       dataObjects.add(data);
    }


    public List<Data> getData(DataType dataType) {
        return dataObjects.stream().filter(data -> data.getDataType() == dataType).collect(Collectors.toList());
    }


    public Data getData(int index) {
        return dataObjects.stream().filter(data -> data.getIndex() == index).findAny().orElse(null);
    }


    /**
     * Get the possible packet name
     *
     * @return The possible packet name
     * if found, or empty string otherwise
     */
    public String getPossiblePacketName() {
        PacketType packetType = null;
        List<Data> strData = getData(DataType.STRING);
        if (strData == null) return "";

        for (Data data : strData) {
            packetType = PacketType.fromPacketName((String) data.getData());
            if (packetType != null) break;
        }

        return (packetType == null) ? "" : packetType.getName();
    }

    @Override
    public String toString() {
        return this.mappings.toString();
    }

    @lombok.Data
    @AllArgsConstructor
    public static class Data {
        private DataType dataType;
        private int index;
        private Object data;
    }
}
