package me.JordanPlayz158.FasterAutoRespondBot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.security.auth.login.LoginException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main 
{
    public static void main(String[] args) throws LoginException, IOException, InterruptedException 
    {
        // Initiates the log
        initiateLog();
        
        //Copy config
        copyFile("config.yml", "config.yml");

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
                .build().awaitReady();
    }

    private static void initiateLog() 
    {
        // Fix for "Failed to load class "org.slf4j.impl.StaticLoggerBinder"
        BasicConfigurator.configure();
        
        // Only log INFO logs as to not make console messy with DEBUG logs
        Logger.getRootLogger().setLevel(Level.INFO);
    }

    private static void copyFile(String internalName, String externalName) throws IOException 
    {
        File fileDest = new File(externalName);
        InputStream fileSrc = Thread.currentThread().getContextClassLoader().getResourceAsStream(internalName);

        Files.copy(fileSrc, fileDest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}