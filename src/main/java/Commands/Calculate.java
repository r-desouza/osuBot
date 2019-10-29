package Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Calculate extends ListenerAdapter {


    public void onGuildMessageReceived( GuildMessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().equalsIgnoreCase("%ip")){

            e.getChannel().sendMessage(e.getGuild().getId()).queue();
            e.getChannel().sendMessage(e.getChannel().getId()).queue();
        }
    }
}
