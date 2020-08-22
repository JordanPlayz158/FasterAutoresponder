package me.JordanPlayz158.FasterAutoRespondBot;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CopyPastaDetection extends ListenerAdapter {

    //List of copypastas
    List<String> copypastas = Arrays.asList("copy & paste this message",
            "copy and paste this message",
            "copy & paste this msg",
            "copy and paste this msg",
            "copy and paste him in every discord server",
            "send this to all the servers you are in",
            "send this to all servers you are in",
            "send this message to all the servers",
            "send this message to all servers",
            "tell everyone on your friends list",
            "do not accept a friend request from",
            "please spread the word of this to your other servers",
            "if you see this user, do not accept his friend request and immediately block him",
            "this is memecat",
            "this is memedog",
            "this is lennypede");

    final File file = new File("warnImage.png");

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getAuthor().isBot())
            return;
    	//Get the message received
        Message msg = event.getMessage();
        
        //Get message's properties
        MessageChannel channel = event.getChannel();
        Guild getGuild = msg.getGuild();
        Member getMember = msg.getMember();
        MessageChannel getChannel = msg.getChannel();

        //Get the guild and channel's properties
        String guildName = getGuild.getName();
        String guildId = getGuild.getId();
        String channelName = getChannel.getName();
        String channelId = getChannel.getId();
        String userName = getMember.getUser().getName();
        String userMention = getMember.getUser().getAsMention();
        String userId = getMember.getUser().getId();
        String messageContent = msg.getContentRaw();

        //Iterate through the copypastas
        for(String s : copypastas) {
        	//Check if message is a copypasta
            if(msg.getContentRaw().toLowerCase().contains(s) && !msg.getAuthor().isBot()) {
            	//Send the warning message
            	MessageAction action = channel.sendMessage(userMention
            			+ Main.loadConfig("config.json", "detectionMessage")
            			+ getGuild.getTextChannelById(Main.loadConfig("config.json", "rulesChannel")).getAsMention());
            	
                if(msg.getContentRaw().toLowerCase().contains("ip")) {
                    action.addFile(file);
                }
                
                action.queue();
                
                //Send the message to copy pasta role
                channel.sendMessage(getGuild.getRoleById(Main.loadConfig("config.json", "warnsRole")).getAsMention()
                		+ "```" + Main.loadConfig("config.json", "warnCommand") + " " + userMention + " " + Main.loadConfig("config.json", "warnMessage") + "```").queue();

                switch(Main.loadConfig("config.json", "logOption").toLowerCase()) {
                    case "discord":
                        LoggerDiscord(event);
                        break;
                    case "console":
                        LoggerConsole(event);
                        break;
                    case "both":
                        LoggerConsole(event);
                        LoggerDiscord(event);
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

    //Adds brackets
    public String autoAddBrackets(String raw) {
        String result = "";
        result += " (";
        result += raw;
        result += ") ";

        return result;
    }

    public void LoggerDiscord(MessageReceivedEvent event) {
        TextChannel textChannel = event.getGuild().getTextChannelById(Main.loadConfig("config.json", "logChannel"));

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

    public void LoggerConsole(MessageReceivedEvent event) {
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
}