package Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class InviteCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived( GuildMessageReceivedEvent e) {
        String[] message = e.getMessage().getContentRaw().split(" ");

        if(message[0].equalsIgnoreCase("%invite") && message.length == 1) {
            e.getChannel().sendMessage("To use %invite do: %invite create").queue();
        } else if (message.length >= 2 && message[0].equalsIgnoreCase("%invite") && message[1].equalsIgnoreCase("create")) {
            e.getChannel().sendMessage("Hey " + e.getAuthor().getName() + " here's your new invite!").queue();
            e.getChannel().sendMessage("kappita que se subeen xD ");
        }

        }
}
