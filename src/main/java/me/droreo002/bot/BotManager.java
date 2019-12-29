package me.droreo002.bot;

import lombok.Getter;
import me.droreo002.bot.models.GrowtopiaBot;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class BotManager {

    @Getter
    private static final List<GrowtopiaBot> bots = new ArrayList<>();
    @Getter
    private static final List<Thread> botThread = new ArrayList<>();
    @Getter
    private static boolean running = false;

    @Nullable
    public static GrowtopiaBot getBot(String botUserName) {
        return bots.stream().filter(growtopiaBot -> growtopiaBot.getUserName().equals(botUserName)).findAny().orElse(null);
    }

    @Nullable
    public static GrowtopiaBot getBot(int index) {
        return bots.get(index);
    }

    public static void registerBot(GrowtopiaBot growtopiaBot) {
        if (running) throw new IllegalStateException("Cannot register bot while running!");
        if (getBot(growtopiaBot.getUserName()) != null) throw new IllegalStateException("Bot with the name of " + growtopiaBot.getUserName() + " already exists!");
        bots.add(growtopiaBot);
    }

    public static void run() {
        running = true;
        for (GrowtopiaBot bot : bots) {
            botThread.add(
                new Thread(() -> {
                    try {
                        bot.eventLoop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
            );
        }

        botThread.forEach(Thread::start);
    }

    public static void stop() {
        running = false;
    }
}
