package me.JordanPlayz158.FasterAutoRespondBot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
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

        List<String> copypastas = Arrays.asList("copy & paste this message", "copy and paste this message", "send this to all the servers you are in", "tell everyone on your friends list", "do not accept a friend request from", "please spread the word of this to your other servers", "if you see this user, do not accept his friend request and immediately block him");

        for(String s : copypastas) {
            if(msg.getContentRaw().toLowerCase().contains(s))
            {
                File file = new File("CopyPasta_Hack_BS_Black.png");
                channel.sendMessage("This message has been auto detected as a copypasta, if this message is about people stealing ips through discord, refer to the image below").queue();
                channel.sendFile(file).queue();
                break;
            }
        }
    }
}
