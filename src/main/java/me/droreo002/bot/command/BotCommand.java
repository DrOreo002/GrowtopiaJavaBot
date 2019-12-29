package me.droreo002.bot.command;

import me.droreo002.bot.models.GameObject;
import me.droreo002.bot.models.GrowtopiaBot;

public interface BotCommand {

    String COMMAND_INDICATOR = "!";

    /**
     * Execute the command
     *
     * @return true if successfully executed, false otherwise
     */
    default boolean execute(GrowtopiaBot bot, GameObject executor, String command, String[] args) {
        return true;
    }

    String getCommand();
}
