package Commands;

import com.oopsjpeg.osu4j.GameMode;
import com.oopsjpeg.osu4j.OsuUser;
import com.oopsjpeg.osu4j.backend.EndpointUsers;
import com.oopsjpeg.osu4j.backend.Osu;
import com.oopsjpeg.osu4j.exception.OsuAPIException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class RanksLeftto4Digit extends ListenerAdapter {


    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {

        Osu osu = Osu.getAPI("424aa3a77eef50a8105cedb68cbb4609f50ea6fe");

        if (e.getMessage().getContentRaw().toLowerCase().contains("%deranker")) {
            if (!e.getMember().getUser().isBot()) {
                OsuUser user = null;
                try {
                    user = osu.users.query(new EndpointUsers.ArgumentsBuilder("Pein").setMode(GameMode.STANDARD).build());
                    e.getChannel().sendMessage("A pein le faltan " + (1000 - user.getRank()) + " ranks para ir a 4 digit con los pibes....").queue();
                } catch (OsuAPIException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }
}