package me.JordanPlayz158.FasterAutoRespondBot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        // Initiates the log
        initiateLog();
        
        //Copy config
        copyFile("config.json", "config.json");

        String token = loadConfig("config.json", "token");
        String activity = loadConfig("config.json", "activity");

        // Checks if the Token is 1 character or less and if so, tell the person they need to provide a token
        if (token.length() <= 1) {
            System.out.println("You have to provide a token in your config file!");
            System.exit(1);
        }

        // Token and activity is read from and set in the config.json
        // This bot only needs to respond to guild messages so it only needs GatewayIntent.GUILD_MESSAGES
        JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new CopyPastaDetection())
                .setActivity(Activity.watching(activity))
                .setLargeThreshold(50)
                .build().awaitReady();
    }

    private static String loadConfig(String file, String key) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(file));
            return (String) jsonObject.get(key);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }

    private static void initiateLog() {
        // Fix for "Failed to load class "org.slf4j.impl.StaticLoggerBinder"
        BasicConfigurator.configure();
        
        // Only log INFO logs as to not make console messy with DEBUG logs
        Logger.getRootLogger().setLevel(Level.DEBUG);
    }

    private static void copyFile(String internalName, String externalName) throws IOException {
        File fileDest = new File(externalName);
        InputStream fileSrc = Thread.currentThread().getContextClassLoader().getResourceAsStream(internalName);

        if(fileDest.createNewFile()) {
            Files.copy(fileSrc, fileDest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}