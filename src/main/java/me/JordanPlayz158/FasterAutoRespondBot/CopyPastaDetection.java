package me.JordanPlayz158.FasterAutoRespondBot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CopyPastaDetection extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();

        List<String> copypastas = Arrays.asList("copy & paste this message", "copy and paste this message", "copy & paste this msg", "copy and paste this msg", "send this to all the servers you are in", "tell everyone on your friends list", "do not accept a friend request from", "please spread the word of this to your other servers", "if you see this user, do not accept his friend request and immediately block him");
        List<String> asciiCopypastas = Arrays.asList("copy and paste him in every discord server");

        for(String s : copypastas) {
            if(msg.getContentRaw().toLowerCase().contains(s))
            {
                Guild getGuild = msg.getGuild();
                Member getMember = msg.getMember();
                MessageChannel getChannel = msg.getChannel();

                File file = new File("CopyPasta_Hack_BS_Black.png");
                channel.sendMessage(getMember.getUser().getAsMention() + "'s message has been auto detected as a copypasta, if this message is about people stealing ips through discord, refer to the image below")
                        .addFile(file).queue();
                channel.sendMessage(getGuild.getRoleById("742447734606528653").getAsMention() + " ```-warn " + getMember.getUser().getAsMention() + " Rule 23```").queue();

                // I don't need to make these strings but I am just for the sake of readability
                String guildName = getGuild.getName();
                String guildId = getGuild.getId();
                String channelName = getChannel.getName();
                String channelId = getChannel.getId();
                String userName = getMember.getUser().getName();
                String userId = getMember.getUser().getId();
                String messageContent = msg.getContentRaw();
                String messageURL = msg.getJumpUrl();

                // I don't need to use so many "" but I used them for the sake of readability
                System.out.println("Log:\nGuild: " + guildName + autoAddBrackets(guildId)
                        + "\nChannel: " + channelName + autoAddBrackets(channelId)
                        + "\nUser: " + userName + autoAddBrackets(userId)
                        + "\nMessage: " + messageContent + autoAddBrackets(messageURL));
                break;
            }
        }
    }

    public String autoAddBrackets(String raw) {
        String result = "";
        result += " (";
        result += raw;
        result += ") ";

        return result;
    }
}
