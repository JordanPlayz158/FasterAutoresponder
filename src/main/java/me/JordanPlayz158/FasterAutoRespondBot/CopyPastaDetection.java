package me.JordanPlayz158.FasterAutoRespondBot;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import me.JordanPlayz158.FasterAutoRespondBot.Util.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class CopyPastaDetection extends ListenerAdapter 
{

    @Override
    public void onMessageReceived(MessageReceivedEvent event) 
    {
    	//Get the message recieved
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
        String userId = getMember.getUser().getId();
        String messageContent = msg.getContentRaw();
        
        //List of copypastas
        List<String> copypastas = Arrays.asList("copy & paste this message", 
            "copy and paste this message", 
            "copy & paste this msg", 
            "copy and paste this msg", 
            "send this to all the servers you are in", 
            "send this message to all the servers", 
            "tell everyone on your friends list", 
            "do not accept a friend request from", 
            "please spread the word of this to your other servers", 
            "if you see this user, do not accept his friend request and immediately block him");
        
        //The ASCII copypastas probably would be merge I guess?
        List<String> asciiCopypastas = Arrays.asList("copy and paste him in every discord server", 
            "this is memecat", 
            "this is memedog", 
            "this is lennypede");

        //Iterate through the copypastas
        for(String s : copypastas) 
        {
        	//Check if message is a copypasta
            if(msg.getContentRaw().toLowerCase().contains(s) && !msg.getAuthor().isBot())
            {
            	//Send the warning message
            	MessageAction action = channel.sendMessage(getMember.getUser().getAsMention() 
            			+ "'s message has been auto detected as a copypasta which is in "
            			+ "violation of rule 23, please read the " 
            			+ getGuild.getTextChannelById("546053935958327316").getAsMention());
            	
                if(msg.getContentRaw().toLowerCase().contains("ip")) 
                {
                    File file = new File(Thread.currentThread()
                    		.getContextClassLoader().getResource("CopyPasta_Hack_BS_Black.png").getFile());
                    action.addFile(file);
                }
                
                action.queue();
                
                //Send the message to copy pasta role
                channel.sendMessage(getGuild.getRoleById("742447734606528653").getAsMention() 
                		+ " ```-warn " + getMember.getUser().getAsMention() + " Rule 23```").queue();

                //Log the message
                String output = Utils.addStrings("Log:\nGuild: ", 
                    guildName,
                    autoAddBrackets(guildId), "\nChannel: ", 
                    channelName,
                    autoAddBrackets(channelId), "\nUser: ", 
                    userName, 
                    autoAddBrackets(userId),
                    "\nMessage: ", 
                    messageContent);

                System.out.println(output);

                LoggerDiscord(event);
                
                //Delete the copypasta
                msg.delete().queue();
                break;
            }
        }

        //This would probably be deleted soon I guess?
        for(String s : asciiCopypastas) 
        {
            if(msg.getContentRaw().toLowerCase().contains(s) && !msg.getAuthor().isBot()) 
            {
                channel.sendMessage(getMember.getUser().getAsMention() 
                    + "'s message has been auto detected as a copypasta which is in violation of rule 23, "
                    + " read the " 
                    + getGuild.getTextChannelById("546053935958327316").getAsMention()).queue();

                channel.sendMessage(getGuild.getRoleById("742447734606528653").getAsMention() + 
                		" ```-warn " + getMember.getUser().getAsMention() + " Rule 23```").queue();

                String output = Utils.addStrings("Log:\nGuild: ",
                    guildName,
                    autoAddBrackets(guildId),
                    "\nChannel: ",
                    channelName, 
                    autoAddBrackets(channelId),
                    "\nUser: " + userName,
                    autoAddBrackets(userId),
                    "\nMessage: ",
                    messageContent);

                System.out.println(output);

                LoggerDiscord(event);
                msg.delete().queue();
                break;
            }
        }
    }

    //Adds brackets
    public String autoAddBrackets(String raw) 
    {
        String result = "";
        result += " (";
        result += raw;
        result += ") ";

        return result;
    }

    public void LoggerDiscord(MessageReceivedEvent event) 
    {
        TextChannel textChannel = event.getGuild().getTextChannelsByName("copypastalogs", true).get(0);

        Message msg = event.getMessage();

        Guild getGuild = msg.getGuild();
        Member getMember = msg.getMember();
        MessageChannel getChannel = msg.getChannel();

        String guildName = getGuild.getName();
        String guildId = getGuild.getId();
        String channelName = getChannel.getName();
        String channelId = getChannel.getId();
        String userName = getMember.getUser().getName();
        String userId = getMember.getUser().getId();
        String messageContent = msg.getContentRaw();

        textChannel.sendMessage("Log:\nGuild: " + guildName + autoAddBrackets(guildId)
                + "\nChannel: " + channelName + autoAddBrackets(channelId)
                + "\nUser: " + userName + autoAddBrackets(userId)
                + "\nMessage: " + messageContent).queue();
    }
}