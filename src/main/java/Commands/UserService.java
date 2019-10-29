package Commands;

import com.oopsjpeg.osu4j.*;
import com.oopsjpeg.osu4j.backend.EndpointUsers;
import com.oopsjpeg.osu4j.backend.Osu;
import com.oopsjpeg.osu4j.exception.OsuAPIException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.List;

public class UserService extends ListenerAdapter {


    private static final int TOP_SCORE_LIMIT = 3;

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {

        Osu osu = Osu.getAPI("424aa3a77eef50a8105cedb68cbb4609f50ea6fe");

        String[] message = e.getMessage().getContentRaw().split(" ");

        if(message.length == 2 && message[0].equalsIgnoreCase("%user")) {

            String USER = message[1];

            try {
                    OsuUser user = osu.users.query(new EndpointUsers.ArgumentsBuilder(USER).setMode(GameMode.STANDARD).build());

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.PINK);
                eb.setThumbnail("https://a.ppy.sh/" + user.getID() + "_1558637759.jpeg");
                eb.setTitle("**"+ user.getUsername() + "** Profile" );



                try {
                    eb.addField("**Profile Link**","<"+ user.getURL()+">", true);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }

                eb.addField("**Rank**","#" + user.getRank() + " (" + user.getPP() + "pp)" + " #" + user.getCountryRank()+ user.getCountry().getAlpha3() , true);

                List<OsuScore> topScores = user.getTopScores(TOP_SCORE_LIMIT).get();

                for (int i = 0; i < topScores.size(); i++) {
                    OsuScore score = topScores.get(i);
                    OsuBeatmap beatmap = score.getBeatmap().get();

                        GameMod[] gm = score.getEnabledMods();
                    String mods = "";
                    if (gm!=null) {
                        for (int k = 0; k < gm.length; k++) {
                            if (k == (gm.length - 1)) {
                                mods = mods + gm[k].getName();
                            } else {
                                mods = mods + gm[k].getName() + ",";
                            }

                        }
                        mods = " **" + mods + "** ";
                    }else{
                        mods = " ** NoMod **";
                    }

                    eb.addField("#"+(i+1)+"  " + beatmap.toString() + mods + " (" + String.format("%.2f",beatmap.getDifficulty())+"✶)", "**" +score.getRank() + "  "+
                            score.getPp()+"pp** x"+ score.getMaxCombo() + "/"+ beatmap.getMaxCombo()  +
                            String.format(" [%d,%d,%d,%d]", score.getHit300(),score.getHit100(),score.getHit50(), score.getMisses() )+ " <" + beatmap.getURL().toString() +">", true);

                }


            e.getChannel().sendMessage(eb.build()).queue();

            } catch (OsuAPIException | MalformedURLException ex) {

                e.getChannel().sendMessage("Nick no existe!").queue();

                ex.printStackTrace();
            }

        }
    }
}