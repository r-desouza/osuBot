package Events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class HelloEvent extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){

        String messageSent = event.getMessage().getContentRaw();

        if(messageSent.contains("exe19")){
            if(!event.getMember().getUser().isBot())
            event.getChannel().sendMessage(":regional_indicator_a::regional_indicator_h::regional_indicator_r::regional_indicator_e:").queue();
            event.getChannel().sendMessage("que pasa compa").queue();

        }
    }

}
