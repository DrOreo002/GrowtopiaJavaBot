package me.droreo002.bot.command.model;

import me.droreo002.bot.BotManager;
import me.droreo002.bot.command.BotCommand;
import me.droreo002.bot.logging.BotLog;
import me.droreo002.bot.models.GameObject;
import me.droreo002.bot.models.GrowtopiaBot;

public class StopCommand implements BotCommand {

    @Override
    public boolean execute(GrowtopiaBot bot, GameObject executor, String command, String[] args) {
        bot.sendMessage("Stopping all bot...");
        BotManager.stop();
        BotLog.log("Bot thread killed...", BotLog.LogType.BOT);
        return true;
    }

    @Override
    public String getCommand() {
        return "stop";
    }
}
