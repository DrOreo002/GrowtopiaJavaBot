package me.droreo002.bot.logging;

import jline.console.ConsoleReader;
import lombok.Getter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class BotLog {

    private static BotLog instance;

    public static void init() {
        if (instance != null) {
            throw new IllegalStateException("Already initialized!");
        }
        System.out.println("Initializing logger..");
        AnsiConsole.systemInstall();
        try {
            ConsoleReader reader = new ConsoleReader();
            reader.setExpandEvents(true);
            instance = new BotLog(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BotLog getInstance() {
        return instance;
    }

    @Getter
    private final Logger logger = Logger.getLogger(BotLog.class.getName());
    @Getter
    private final ConsoleReader consoleReader;
    @Getter
    private final Set<UIConsole> consoles;

    private BotLog(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
        this.consoles = new HashSet<>();

        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
        logger.setUseParentHandlers(false);

        BotLogHandler handler = new BotLogHandler(consoleReader);
        handler.setFormatter(new BotLogFormatter());
        logger.addHandler(handler);
    }

    public void info(String msg, LogType logType) {
        for (UIConsole c : this.consoles) {
            c.print(msg, logType);
        }
        logger.log(Level.INFO, msg);
    }

    public UIConsole getUIConsole(String name) {
        return this.consoles.stream().filter(uiConsole -> uiConsole.getConsoleName().equals(name)).findAny().orElse(null);
    }

    public void registerUIConsole(UIConsole uiConsole) {
        if (getUIConsole(uiConsole.getConsoleName()) != null) throw new IllegalStateException("UI Console with the name of " + uiConsole.getConsoleName() + " is already registered!");
        this.consoles.add(uiConsole);
    }

    public static void log(String s, LogType logType) {
        BotLog.getInstance().info(s, logType);
    }

    public static void log(int s, LogType logType) {
        log(Integer.toString(s), logType);
    }

    private static class BotLogFormatter extends Formatter {

        // ANSI escape code
        static final String ANSI_RESET = Ansi.ansi().a(Ansi.Attribute.RESET).toString();
        static final String ANSI_GREEN = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString();
        static final String ANSI_WHITE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString();

        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder();
            builder.append(ANSI_GREEN);

            builder.append("[");
            builder.append(calcDate(record.getMillis()));
            builder.append(" ");
            builder.append(record.getLevel().getName());
            builder.append("]:");

            builder.append(ANSI_WHITE);
            builder.append(" ");
            builder.append(record.getMessage());

            return builder.toString();
        }

        private String calcDate(long millisecs) {
            SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
            Date resultdate = new Date(millisecs);
            return date_format.format(resultdate);
        }
    }

    public enum LogType {
        BOT, // Both
        PACKET, // All packet related, except for TANK_PACKET
        TANK_PACKET // Specified
    }
}
