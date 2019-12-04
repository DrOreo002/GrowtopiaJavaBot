package me.droreo002.bot.utils;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class BotLog {

    private static BotLog instance;

    public static BotLog getInstance() {
        if (instance == null) {
            instance = new BotLog();
            return instance;
        }
        return instance;
    }

    @Getter
    private final Logger logger = Logger.getLogger(BotLog.class.getName());

    private BotLog() {
        Handler[] handlers = logger.getHandlers();
        for(Handler handler : handlers) {
            logger.removeHandler(handler);
        }
        logger.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new BotLogFormatter());
        logger.addHandler(handler);
    }

    public void info(String msg) {
        logger.log(Level.INFO, msg);
    }

    private static class BotLogFormatter extends Formatter {

        // ANSI escape code
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_WHITE = "\u001B[37m";

        @Override
        public String format(LogRecord record) {
            // This example will print date/time, class, and log level in yellow,
            // followed by the log message and it's parameters in white .
            StringBuilder builder = new StringBuilder();
            builder.append(ANSI_BLUE);

            builder.append("[");
            builder.append(calcDate(record.getMillis()));
            builder.append(" ");
            builder.append(record.getLevel().getName());
            builder.append("]:");

            builder.append(ANSI_WHITE);
            builder.append(" ");
            builder.append(record.getMessage());

            Object[] params = record.getParameters();

            if (params != null) {
                builder.append("\t");
                for (int i = 0; i < params.length; i++) {
                    builder.append(params[i]);
                    if (i < params.length - 1)
                        builder.append(", ");
                }
            }

            builder.append(ANSI_RESET);
            builder.append("\n");
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
