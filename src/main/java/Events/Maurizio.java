package Events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Maurizio extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){

        String messageSent = event.getMessage().getContentRaw();

        if(messageSent.contains("dami")){
            if(!event.getMember().getUser().isBot())
                event.getChannel().sendMessage("la concha de tu madre").queue();
            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            event.getChannel().sendMessage("puto").queue();

        }
    }

}