package me.jordanplayz158.fasterautoresponder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Objects;

public class CopyPastaDetection extends ListenerAdapter {
    private static final Main instance = Main.getInstance();
    private final String prefix = instance.getConfig().getPrefix();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getAuthor().isBot())
            return;

        if (Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById(instance.getConfig().getWarnRole()))) {
            if (event.getMessage().getContentRaw().startsWith(prefix)) {
                String extractPrefix = event.getMessage().getContentRaw().substring(prefix.length());

                if (extractPrefix.startsWith("addString")) {
                    String text = event.getMessage().getContentRaw().substring(prefix.length() + 10);

                    /* Redo this system
                     * Start System
                     *
                     */
                    JsonArray copypastas = new JsonArray();

                    for (Object copypasta : instance.getConfig().getCopypastas()) {
                        copypastas.add(copypasta.toString());
                    }

                    copypastas.add(text);

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    Writer writer = null;
                    try {
                        writer = Files.newBufferedWriter(instance.getCopypastasFile().toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JsonObject detectionStrings = new JsonObject();
                    detectionStrings.add("detectionStrings", copypastas);

                    gson.toJson(detectionStrings, writer);

                    try {
                        assert writer != null;
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*
                     *
                     * End System
                     */

                    event.getChannel().sendMessage("String has been added to list").queue();
                    return;
                }
            }
        }

        //Get the message received
        Message msg = event.getMessage();

        //Get message's properties
        MessageChannel channel = msg.getChannel();
        Guild getGuild = msg.getGuild();
        Member getMember = Objects.requireNonNull(msg.getMember());

        //Get the guild and channel's properties
        String userMention = getMember.getUser().getAsMention();

        //Iterate through the copypastas
        for (Object copypasta : instance.getConfig().getCopypastas()) {
            String s = copypasta.toString();

            //Check if message is a copypasta
            if (msg.getContentRaw().toLowerCase().contains(s)) {
                // Swap out placeholders for real values
                StringBuilder warnMessage = new StringBuilder(instance.getConfig().getDetectionMessage());

                warnMessage = replacePlaceholders(warnMessage, event);

                //Send the warning message
                MessageAction action = channel.sendMessage(warnMessage.toString());

                if (msg.getContentRaw().toLowerCase().contains("ip")) {
                    action.addFile(instance.getWarnImageFile());
                }

                action.queue();

                //Send the message to copy pasta role
                StringBuilder staffWarnMessage = new StringBuilder(instance.getConfig().getStaffWarnMessage());

                staffWarnMessage = replacePlaceholders(staffWarnMessage, event);

                channel.sendMessage(staffWarnMessage).queue();

                switch (instance.getConfig().getLogChannelOption().toLowerCase()) {
                    case "discord":
                        new Loggers(event, true, false);
                        break;
                    case "console":
                        new Loggers(event, false, true);
                        break;
                    case "both":
                        new Loggers(event, true, true);
                        break;
                    default:
                        break;
                }

                //Delete the copypasta
                msg.delete().queue();
                break;
            }
        }
    }

    public StringBuilder replacePlaceholders(StringBuilder stringBuilder, MessageReceivedEvent event) {
        while (stringBuilder.indexOf("%userMention%") != -1) {
            stringBuilder.replace(stringBuilder.indexOf("%userMention%"),
                    stringBuilder.indexOf("%userMention%") + "%userMention%".length(),
                    event.getAuthor().getAsMention());
        }

        while (stringBuilder.indexOf("%warnRole%") != -1) {
            stringBuilder.replace(stringBuilder.indexOf("%warnRole%"),
                    stringBuilder.indexOf("%warnRole%") + "%warnRole%".length(),
                    Objects.requireNonNull(event.getGuild().getRoleById(
                            Main.getInstance().getConfig().getWarnRole())).getAsMention());
        }

        while (stringBuilder.indexOf("%rulesChannelMention%") != -1) {
            stringBuilder.replace(stringBuilder.indexOf("%rulesChannelMention%"),
                    stringBuilder.indexOf("%rulesChannelMention%") + "%rulesChannelMention%".length(),
                    Objects.requireNonNull(event.getGuild().getTextChannelById(
                            Main.getInstance().getConfig().getRulesChannel())).getAsMention());
        }

        return stringBuilder;
    }
}