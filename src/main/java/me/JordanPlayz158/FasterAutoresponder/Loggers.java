package me.JordanPlayz158.FasterAutoresponder;

import me.JordanPlayz158.Utils.loadJson;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Loggers {
    public static void LoggerDiscord(MessageReceivedEvent event) {
        TextChannel textChannel = event.getGuild().getTextChannelById(loadJson.value(Variables.config, "logChannel"));

        //Get the message received
        Message msg = event.getMessage();

        //Get message's properties
        Guild getGuild = msg.getGuild();
        Member getMember = msg.getMember();

        //Get the guild and channel's properties
        String guildName = getGuild.getName();
        String guildId = getGuild.getId();
        String channelMention = msg.getTextChannel().getAsMention();
        String userMention = getMember.getAsMention();
        String messageContent = msg.getContentRaw();

        textChannel.sendMessage("Guild: " + guildName + autoAddBrackets(guildId)
                + "\nChannel: " + channelMention
                + "\nUser: " + userMention
                + "\nMessage: " + messageContent).queue();
    }

    public static void LoggerConsole(MessageReceivedEvent event) {
        //Get the message received
        Message msg = event.getMessage();

        //Get message's properties
        Guild getGuild = msg.getGuild();
        Member getMember = msg.getMember();
        MessageChannel getChannel = msg.getChannel();

        //Get the guild and channel's properties
        String guildName = getGuild.getName();
        String guildId = getGuild.getId();
        String channelName = getChannel.getName();
        String channelId = getChannel.getId();
        String userName = getMember.getUser().getName();
        String userId = getMember.getUser().getId();
        String messageContent = msg.getContentRaw();

        //Log the message
        System.out.println("Log:\nGuild: "
                + guildName
                + autoAddBrackets(guildId) + "\nChannel: "
                + channelName
                + autoAddBrackets(channelId) + "\nUser: "
                + userName
                + autoAddBrackets(userId)
                + "\nMessage: "
                + messageContent);
    }

    //Adds brackets
    public static String autoAddBrackets(String raw) {
        String result = "";
        result += " (";
        result += raw;
        result += ") ";

        return result;
    }
}
