package Events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class menem extends ListenerAdapter {
    int m;


    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String messageSent = e.getMessage().getContentRaw();



        if(messageSent.contains("menem")){


            Random rand = new Random();

            int n;

            do{
                n = rand.nextInt(4) + 1;
            }while (n==m);
            switch(n) {
                case 1:
                    e.getChannel().sendMessage("la puta que te pario").queue();
                    break;
                case 2:
                    e.getChannel().sendMessage( "hijo de puta").queue();
                    break;
                case 3:
                    e.getChannel().sendMessage( "judio mala leche").queue();
                    break;
                case 4:
                    e.getChannel().sendMessage(  "te cruzo y te hundo el pecho").queue();
                    break;
            }

            m=n;
        }

    }


}
