package me.droreo002.bot;

import enetjava.ENetLib;
import me.droreo002.bot.command.BotCommandHandler;
import me.droreo002.bot.models.BotData;
import me.droreo002.bot.models.GrowtopiaBot;
import me.droreo002.bot.logging.BotLog;

public class BotLauncher {

    public static void main(String[] args) {
        // Server is on main thread
        try {
            runDefault(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runDefault(boolean waitForInput) throws Exception {
        BotData botData = BotData.get("O:\\bot.json");
        ENetLib.initialize(botData.getEnetLibPath(), true); //change this line
        ENetLib.enet_initialize();

        /*
		Init
		 */
        BotLog.init();
        BotCommandHandler.initDefault();

        GrowtopiaBot bot = new GrowtopiaBot(botData);
        bot.connect("145.239.149.135", 1, 0, 0);
        BotManager.registerBot(bot);

        BotManager.run();

        if (waitForInput) {
            String line;
            while ((line = BotLog.getInstance().getConsoleReader().readLine(">")) != null) {
                BotLog.log(line, BotLog.LogType.BOT);
            }
        }
    }
}
