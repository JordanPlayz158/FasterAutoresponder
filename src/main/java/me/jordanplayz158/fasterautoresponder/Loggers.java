package me.jordanplayz158.fasterautoresponder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Objects;

public class Loggers {
    // For Discord
    private TextChannel textChannel;
    //

    private final String guildName;
    private final String guildId;

    // For Console
    private String channelName;
    private String channelId;
    private String userName;
    private String userId;
    //

    // For Discord
    String channelMention;
    String userMention;
    //

    String messageContent;

    public Loggers(MessageReceivedEvent event, boolean discord, boolean console) {
        // Get the message received
        Message msg = event.getMessage();

        // Get message's properties
        Guild guild = msg.getGuild();
        Member member = Objects.requireNonNull(msg.getMember());

        //Get the guild and channel's properties
        guildName = guild.getName();
        guildId = guild.getId();

        messageContent = msg.getContentRaw();

        if(discord) {
            textChannel = event.getGuild().getTextChannelById(Main.getInstance().getConfig().getLogChannelId());
            channelMention = msg.getTextChannel().getAsMention();
            userMention = member.getAsMention();

            LoggerDiscord();
        }

        if(console) {
            // For Console
            MessageChannel channel = msg.getChannel();
            channelName = channel.getName();
            channelId = channel.getId();
            userName = member.getUser().getName();
            userId = member.getUser().getId();

            LoggerConsole();
        }

    }

    public void LoggerDiscord() {
        textChannel.sendMessage(new EmbedBuilder()
            .setTitle("Copypasta Detected")
            .addField("Guild", guildName + autoAddParenthesis(guildId), false)
            .addField("Channel", channelMention, false)
            .addField("User", userMention, false)
            .addField("Message", messageContent, false)
            .setColor(Color.YELLOW)
            .build()
        ).queue();
    }

    public void LoggerConsole() {
        //Log the message
        System.out.println("Log:\nGuild: "
                + guildName
                + autoAddParenthesis(guildId) + "\nChannel: "
                + channelName
                + autoAddParenthesis(channelId) + "\nUser: "
                + userName
                + autoAddParenthesis(userId)
                + "\nMessage: "
                + messageContent
                + "\n");
    }

    //Adds brackets
    public static String autoAddParenthesis(String raw) {
        String result = "";
        result += " (";
        result += raw;
        result += ") ";

        return result;
    }
}
