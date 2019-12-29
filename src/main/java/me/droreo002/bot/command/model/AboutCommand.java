package me.droreo002.bot.command.model;

import me.droreo002.bot.command.BotCommand;
import me.droreo002.bot.models.GameObject;
import me.droreo002.bot.models.GrowtopiaBot;

public class AboutCommand implements BotCommand {

    @Override
    public boolean execute(GrowtopiaBot bot, GameObject executor, String command, String[] args) {
        bot.sendMessage("GrowtopiaBot v1.0, Made by DrOreo002 with the help of GrowtopiaNoobs");
        return true;
    }

    @Override
    public String getCommand() {
        return "about";
    }
}
