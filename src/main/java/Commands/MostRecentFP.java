package Commands;

import com.google.gson.*;
import com.oopsjpeg.osu4j.*;
import com.oopsjpeg.osu4j.backend.EndpointUsers;
import com.oopsjpeg.osu4j.backend.Osu;
import com.oopsjpeg.osu4j.exception.OsuAPIException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.annotation.Nonnull;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class MostRecentFP extends ListenerAdapter {


    private static final int TOP_SCORE_LIMIT = 3;


    String a = "buenas tardes";



    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {

        Osu osu = Osu.getAPI("424aa3a77eef50a8105cedb68cbb4609f50ea6fe");

        String[] message = e.getMessage().getContentRaw().split(" ");

        if(message.length == 2 && message[0].equalsIgnoreCase("%fp")) {

            String USER = message[1];

            try {

                OsuUser user = osu.users.query(new EndpointUsers.ArgumentsBuilder(USER).setMode(GameMode.STANDARD).build());


                //intento 1

                final URI u;
                try {
                    u = new URI("https://osu.ppy.sh/users/" + user.getID());
                    org.jsoup.nodes.Document doc = Jsoup.connect(String.valueOf(u)).get();
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }

                org.jsoup.nodes.Document doc = null;
                org.jsoup.nodes.Element beatmapsJson = doc.getElementById("json-extras");
                DataNode scriptContent = (DataNode) beatmapsJson.childNodes().get(1);

                System.out.println(beatmapsJson.childNodes().get(1).toString());

                String jsonDeMierda = scriptContent.getWholeData();

                JsonElement je = new JsonParser().parse(jsonDeMierda);

                JsonArray bmsets = je.getAsJsonObject().getAsJsonArray("beatmapsets");




                //CUERPO DEL MENSAJE
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

                /*
                for (JsonElement bm : bmsets) {

                    JsonObject bmObject = bm.getAsJsonObject();

                    String id = bmObject.getAsJsonPrimitive("id").getAsString();

                    //BeatmapJSON[] beatmap = new Gson().fromJson(BeatmapJSON[].class);
                    eb.addField("Mapa id", id, true);

                }
*/


                e.getChannel().sendMessage(eb.build()).queue();

            } catch (OsuAPIException | MalformedURLException ex) {

                e.getChannel().sendMessage("Nick no existe!").queue();

                ex.printStackTrace();
            }

        }
    }
}