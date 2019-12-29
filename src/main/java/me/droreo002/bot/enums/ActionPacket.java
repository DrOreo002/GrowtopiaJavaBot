package me.droreo002.bot.enums;

import lombok.Getter;
import me.droreo002.bot.utils.PacketStringBuilder;

public enum ActionPacket {

    INPUT_TEXT("action|input\n|text|");

    @Getter
    private String actionHeader;

    ActionPacket(String actionHeader) {
        this.actionHeader = actionHeader;
    }

    public PacketStringBuilder asBuilder() {
        return PacketStringBuilder.init().append(actionHeader);
    }
}
