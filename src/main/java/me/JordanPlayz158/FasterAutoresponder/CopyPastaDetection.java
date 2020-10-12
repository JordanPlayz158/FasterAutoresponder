package me.JordanPlayz158.FasterAutoresponder;

import me.JordanPlayz158.Utils.loadJson;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;

public class CopyPastaDetection extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getAuthor().isBot())
            return;

        if(event.getMember().getRoles().stream().anyMatch(role -> role.equals(event.getGuild().getRoleById(loadJson.value(Variables.config, Variables.warnsRole))))) {
            if (event.getMessage().getContentRaw().startsWith(loadJson.value(Variables.config, Variables.warnsRole) + "addString")) {
                ArrayList array = loadJson.array(Variables.copypastasFile, "detectionStrings");
                array.add(event.getMessage().getContentRaw().substring(loadJson.value(Variables.config, "prefix").length() + 10));

                System.out.println(array);
            }
        }

    	//Get the message received
        Message msg = event.getMessage();
        
        //Get message's properties
        MessageChannel channel = msg.getChannel();
        Guild getGuild = msg.getGuild();
        Member getMember = msg.getMember();

        //Get the guild and channel's properties
        String userMention = getMember.getUser().getAsMention();

        //Iterate through the copypastas
        for(String s : Variables.copypastas) {
        	//Check if message is a copypasta
            if(msg.getContentRaw().toLowerCase().contains(s)) {
            	//Send the warning message
            	MessageAction action = channel.sendMessage(userMention
            			+ loadJson.value(Variables.config, "detectionMessage")
            			+ getGuild.getTextChannelById(loadJson.value(Variables.config, "rulesChannel")).getAsMention());
            	
                if(msg.getContentRaw().toLowerCase().contains("ip")) {
                    action.addFile(Variables.file);
                }
                
                action.queue();
                
                //Send the message to copy pasta role
                channel.sendMessage(getGuild.getRoleById(loadJson.value(Variables.config, Variables.warnsRole)).getAsMention()
                		+ "```" + loadJson.value(Variables.config, "warnCommand") + " " + userMention + " " + loadJson.value(Variables.config, "warnMessage") + "```").queue();

                switch(loadJson.value(Variables.config, "logOption").toLowerCase()) {
                    case "discord":
                        Loggers.LoggerDiscord(event);
                        break;
                    case "console":
                        Loggers.LoggerConsole(event);
                        break;
                    case "both":
                        Loggers.LoggerConsole(event);
                        Loggers.LoggerDiscord(event);
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
}