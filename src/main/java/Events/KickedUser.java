package Events;

import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class KickedUser extends ListenerAdapter {

    public void onKickedUser(GuildLeaveEvent e){
        if(e.getGuild().getMemberByTag("Fly#9504").getUser() != null ) {
            e.getGuild().getDefaultChannel().sendMessage("Maurizio se fue... y bueno nada :sleeping_accommodation: ").queue();
        }


    }




}
