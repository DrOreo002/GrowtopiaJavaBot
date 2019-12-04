package me.droreo002.bot;

import com.google.gson.Gson;
import enetjava.ENetLib;
import jline.console.ConsoleReader;
import me.droreo002.bot.models.GrowtopiaBot;
import me.droreo002.bot.utils.BotLog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class Launcher {

    public static void main(String[] args) {
        // Server is on main thread
        try {
            new Launcher().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws Exception {
        Map<String, String> botData = readBotData();
        ENetLib.initialize(botData.get("enetLibPath"), true); //change this line
        ENetLib.enet_initialize();

        /*
		Init
		 */
        BotLog.getInstance();

        GrowtopiaBot bot = new GrowtopiaBot(botData.get("botUsername"), botData.get("botPassword"));
        bot.connect("209.59.191.86", 17093, 0, 0);
        BotManager.registerBot(bot);

        BotManager.run();
    }

    private Map<String, String> readBotData() throws FileNotFoundException {
        String path = "O:\\bot.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        Gson gson = new Gson();
        Map<String, String> json = gson.fromJson(bufferedReader, Map.class);
        return json;
    }
}
