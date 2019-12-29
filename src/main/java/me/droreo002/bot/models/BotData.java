package me.droreo002.bot.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@AllArgsConstructor
public class BotData {
    @Getter
    private String botUsername, botPassword;
    @Getter
    private String enetLibPath;

    public void save(String jsonPath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(new File(jsonPath));
        gson.toJson(this, fileWriter);
    }

    public static BotData get(String jsonPath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader reader = new FileReader(new File(jsonPath));
        return gson.fromJson(reader, BotData.class);
    }
}
