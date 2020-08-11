package me.JordanPlayz158.FasterAutoRespondBot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws LoginException, IOException {
        // Initiates the log
        initiateLog();

        File config = new File("config.yml");
        File image = new File ("CopyPasta_Hack_BS_Black.png");
        copyFile(config, config);
        copyFile(image, image);

        // Checks if the Token is less than 1 character and if so, tell the person they need to provide a token
        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }

        // args[0] should be the token -------> Token will soon be going into a config.yml and read from it
        // This bot only needs to respond to guild messages so it only needs GatewayIntent.GUILD_MESSAGES
        JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new CopyPastaDetection())
                .setActivity(Activity.watching("For CopyPastas"))
                .setLargeThreshold(50)
                .build();
    }

    private static void initiateLog() {
        // Fix for "Failed to load class "org.slf4j.impl.StaticLoggerBinder"
        BasicConfigurator.configure();
        // Only log INFO logs as to not make console messy with DEBUG logs
        Logger.getRootLogger().setLevel(Level.INFO);
    }

    private static void copyFile(File source, File dest) throws IOException {
        if(dest.createNewFile()) {
            Files.copy(source.toPath(), dest.toPath());
        }
    }
}