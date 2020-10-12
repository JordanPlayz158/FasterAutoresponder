package me.JordanPlayz158.FasterAutoresponder;

import me.JordanPlayz158.Utils.loadJson;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.IOException;

import static me.JordanPlayz158.Utils.copyFile.copyFile;
import static me.JordanPlayz158.Utils.initiateLog.initiateLog;

public class Main {
    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        // Initiates the log
        initiateLog();
        
        //Copy config
        copyFile(Variables.config, Variables.config);
        copyFile(Variables.copypastasFile, Variables.copypastasFile);
        copyFile(Variables.file.getName(), Variables.file.getName());

        String token = loadJson.value(Variables.config, "token");
        String activity = loadJson.value(Variables.config, "activity");
        String activityType = loadJson.value(Variables.config, "activityType");

        // Checks if the Token is 1 character or less and if so, tell the person they need to provide a token
        if (token.length() <= 1) {
            System.out.println("You have to provide a token in your config file!");
            System.exit(1);
        }

        // Token and activity is read from and set in the config.json
        // This bot only needs to respond to guild messages so it only needs GatewayIntent.GUILD_MESSAGES
        JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new CopyPastaDetection())
                .setActivity(Activity.of(Activity.ActivityType.valueOf(activityType.toUpperCase()), activity))
                .build()
                .awaitReady();
    }
}