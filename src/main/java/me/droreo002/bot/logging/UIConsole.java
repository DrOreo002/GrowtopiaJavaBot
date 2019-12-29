package me.droreo002.bot.logging;

public interface UIConsole {
    void print(String msg, BotLog.LogType logType);
    String getConsoleName();
}
