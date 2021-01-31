package me.jordanplayz158.fasterautoresponder;

import me.jordanplayz158.fasterautoresponder.json.Config;
import me.jordanplayz158.utils.FileUtils;
import me.jordanplayz158.utils.Initiate;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class Main {
    // Files
    private final File configFile = new File("config.json");
    private final File warnImageFile = new File("warnImage.png");
    private final File copypastasFile = new File("copypastas.json");

    private static Config config;

    private static final Main instance = new Main();

    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        // Copy all needed files
        FileUtils.copyFile(instance.configFile, instance.configFile);
        FileUtils.copyFile(instance.copypastasFile, instance.copypastasFile);
        FileUtils.copyFile(instance.warnImageFile, instance.warnImageFile);

        // Gets the config file values
        config = new Config();
        final String token = config.getJson().get("token").getAsString();

        // Initiates the log
        Logger logger = Initiate.log();

        // Checks if the Token is 1 character or less and if so, tell the person they need to provide a token
        if (token.length() <= 1) {
            logger.fatal("You have to provide a token in your config file!");
            System.exit(1);
        }

        // Token and activity is read from and set in the config.json
        // This bot only needs to respond to guild messages so it only needs GatewayIntent.GUILD_MESSAGES
        JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new CopyPastaDetection())
                .setActivity(Activity.of(config.getActivityType(), config.getActivityName()))
                .build()
                .awaitReady();
    }

    public static Main getInstance() {
        return instance;
    }

    public File getConfigFile() {
        return configFile;
    }

    public File getCopypastasFile() {
        return copypastasFile;
    }

    public File getWarnImageFile() {
        return warnImageFile;
    }

    public Config getConfig() {
        return config;
    }
}