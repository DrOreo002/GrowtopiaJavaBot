package me.droreo002.bot.logging;

import jline.console.ConsoleReader;
import lombok.Getter;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class BotLogHandler extends Handler {

    @Getter
    private final ConsoleReader consoleReader;

    BotLogHandler(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    @Override
    public void publish(LogRecord record) {
        if (isLoggable(record)) {
            try {
                System.out.println(Ansi.ansi().eraseLine(Ansi.Erase.ALL).toString() + ConsoleReader.RESET_LINE + getFormatter().format(record) + Ansi.ansi().reset().toString());
                consoleReader.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
