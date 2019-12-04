package me.droreo002.bot.logging;

import jline.console.ConsoleReader;
import lombok.Getter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class BotLog {

    private static BotLog instance;

    public static void init() {
        if (instance != null) throw new IllegalStateException("Already initialized!");
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

    private BotLog(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
        logger.setUseParentHandlers(false);

        BotLogHandler handler = new BotLogHandler(consoleReader);
        handler.setFormatter(new BotLogFormatter());
        logger.addHandler(handler);
    }

    public void info(String msg) {
        logger.log(Level.INFO, msg);
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

            builder.append(ANSI_RESET);
            return builder.toString();
        }

        private String calcDate(long millisecs) {
            SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
            Date resultdate = new Date(millisecs);
            return date_format.format(resultdate);
        }
    }

    public static void log(String s) {
        BotLog.getInstance().info(s);
    }

    public static void log(int s) {
        log(Integer.toString(s));
    }
}
