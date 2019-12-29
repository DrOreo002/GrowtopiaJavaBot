package me.droreo002.bot.command;

import me.droreo002.bot.command.model.AboutCommand;
import me.droreo002.bot.command.model.StopCommand;
import me.droreo002.bot.logging.BotLog;
import me.droreo002.bot.models.GameObject;
import me.droreo002.bot.models.GrowtopiaBot;

import java.util.ArrayList;
import java.util.List;

public final class BotCommandHandler {

    private static final List<BotCommand> COMMANDS = new ArrayList<>();

    public static void registerCommand(BotCommand command) {
        if (getCommand(command.getCommand()) != null) throw new IllegalStateException("Command with the name of " + command.getCommand() + " already registered!");
        COMMANDS.add(command);
    }

    public static BotCommand getCommand(String command) {
        return COMMANDS.stream().filter(cmd -> cmd.getCommand().equals(command)).findAny().orElse(null);
    }

    public static void handle(GrowtopiaBot bot, String str) {
       handle(bot, str, null);
    }

    public static void handle(GrowtopiaBot bot, String str, GameObject executor) {
        int separator = str.indexOf("=");
        if (separator < 0) return;
        String[] data = str.substring(separator + 1).split(" ");
        String command = data[0];
        if (!command.startsWith(BotCommand.COMMAND_INDICATOR)) return;
        BotCommand handler = getCommand(command.replace(BotCommand.COMMAND_INDICATOR, ""));
        if (handler != null) {
            if (!handler.execute(bot, executor, command, data)) {
                bot.sendMessage("Failed to executor command....");
            }
        } else {
            bot.sendMessage("Cannot find command " + command);
        }
    }

    public static void initDefault() {
        registerCommand(new AboutCommand());
        registerCommand(new StopCommand());
    }
}
