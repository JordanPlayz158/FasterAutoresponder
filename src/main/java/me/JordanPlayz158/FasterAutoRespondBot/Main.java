package me.JordanPlayz158.FasterAutoRespondBot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);

        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }

        // args[0] should be the token
        // We only need 2 intent in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new Main())
                .setActivity(Activity.watching("For CopyPastas"))
                .setLargeThreshold(50)
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();

        List<String> copypastas = Arrays.asList("copy & paste this message", "copy and paste this message", "copy & paste this msg", "copy and paste this msg", "send this to all the servers you are in", "tell everyone on your friends list", "do not accept a friend request from", "please spread the word of this to your other servers", "if you see this user, do not accept his friend request and immediately block him");

        for(String s : copypastas) {
            if(msg.getContentRaw().toLowerCase().contains(s))
            {
                File file = new File("CopyPasta_Hack_BS_Black.png");
                channel.sendMessage("This message has been auto detected as a copypasta, if this message is about people stealing ips through discord, refer to the image below").queue();
                channel.sendFile(file).queue();

                // I don't need to make these strings but I am just for the sake of readability
                Guild GetGuild = msg.getGuild();
                Member GetMember = msg.getMember();
                MessageChannel GetChannel = msg.getChannel();

                String GuildName = GetGuild.getName();
                String GuildId = GetGuild.getId();
                String ChannelName = GetChannel.getName();
                String ChannelId = GetChannel.getId();
                String UserName = GetMember.getUser().getName();
                String UserId = GetMember.getUser().getId();
                String MessageContent = msg.getContentRaw();
                String MessageURL = msg.getJumpUrl();

                // I don't need to use so many "" but I used them for the sake of readability
                System.out.println("Log:\nGuild: " + GuildName + autoAddBrackets(GuildId)
                        + "\nChannel: " + ChannelName + autoAddBrackets(ChannelId)
                        + "\nUser: " + UserName + autoAddBrackets(UserId)
                        + "\nMessage: " + MessageContent + autoAddBrackets(MessageURL));
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