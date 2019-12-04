import Commands.*;
import Events.*;
import com.google.gson.*;
import com.oopsjpeg.osu4j.GameMode;
import com.oopsjpeg.osu4j.OsuBeatmap;
import com.oopsjpeg.osu4j.OsuBeatmapSet;
import com.oopsjpeg.osu4j.OsuUser;
import com.oopsjpeg.osu4j.backend.EndpointBeatmapSet;
import com.oopsjpeg.osu4j.backend.EndpointBeatmaps;
import com.oopsjpeg.osu4j.backend.EndpointUsers;
import com.oopsjpeg.osu4j.backend.Osu;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Bot {

    public static void main(String args[]) throws Exception{

        //osu api key
        String apik = "";

        JDA jda = new JDABuilder(apik).build();

        jda.awaitReady();

        //comandos

        jda.addEventListener(new HelloEvent());

        jda.addEventListener(new KickedUser());

        jda.addEventListener(new Calculate());

        jda.addEventListener(new InviteCommand());

        jda.addEventListener(new Maurizio());

        jda.addEventListener(new MaurizioImages());

        jda.addEventListener(new menem());

        jda.addEventListener(new UserService());

        jda.addEventListener(new RanksLeftto4Digit());

        jda.addEventListener(new MostRecentFP());

        final Path path = Paths.get("bmdata.txt");

        final Path pathapikey = Paths.get("apikey.txt");



        if(!Files.exists(pathapikey))
            Files.createFile(pathapikey);


        String apikey = "";

//NEW RANKED MAP

        if(!Files.exists(path))
            Files.createFile(path);

        try {

            List<String> ls = Files.readAllLines(path, StandardCharsets.UTF_8);

            while (true) {

                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                //parse de la pagina web de ranked maps
                Document document = documentBuilder.parse("https://osu.ppy.sh/feed/ranked/");

                System.out.println("Root element :" + document.getDocumentElement().getNodeName());

                NodeList nList = document.getElementsByTagName("item");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        String[] tokens = eElement.getElementsByTagName("link").item(0).getTextContent().split("\\/");
                        String id = tokens[tokens.length - 1];

                        if (!ls.contains(id)) {

                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
                            eb.setColor(Color.PINK);
                            eb.setThumbnail("http://b.ppy.sh/thumb/" + id + "l.jpg");
                            eb.addField("Ranked date", eElement.getElementsByTagName("pubDate").item(0).getTextContent(), true);
                            eb.addField("Beatmap by: ", eElement.getElementsByTagName("author").item(0).getTextContent(), true);
                            eb.addField("Beatmap link: ", eElement.getElementsByTagName("link").item(0).getTextContent(), true);

                            URL url = new URL("https://osu.ppy.sh/api/get_beatmaps?k=" + apikey + "&s=" + id);
                            HttpURLConnection request = (HttpURLConnection) url.openConnection();

                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(request.getInputStream()));
                            String inputLine;
                            StringBuffer response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }

                            in.close();

                            BeatmapJSON[] beatmap = new Gson().fromJson(response.toString(), BeatmapJSON[].class);

                            eb.addField("BPM", "☆" + beatmap[0].getBpm(), true);

                            float longVal = beatmap[0].getTotal_length();
                            int hours = (int) longVal / 3600;
                            int remainder = (int) longVal - hours * 3600;
                            int mins = remainder / 60;
                            remainder = remainder - mins * 60;
                            int secs = remainder;

                            int[] ints = {hours, mins, secs};

                            if (hours <= 0) {
                                if(mins>9){
                                    eb.addField("Length", ints[1] + ":" + ints[2], true);
                                }else{
                                eb.addField("Length", ints[1] + ":" + ints[2], true);
                                }
                            } else {
                                eb.addField("Length", ints[0] + ":" + ints[1] + ":" + String.format("%02d", ints[2]), true);
                            }


                            float highest = 0;
                            String highest_id = "";

                            for (BeatmapJSON bm : beatmap) {
                                if (bm.getDifficultyrating() > highest) {
                                    highest = bm.getDifficultyrating();
                                    highest_id = bm.getBeatmap_id();
                                }
                            }

                            for (BeatmapJSON bm : beatmap) {
                                if (bm.getBeatmap_id() == highest_id) {
                                    eb.addField(":fire: " + bm.getVersion() + " :fire:\n" + String.format("    %.2f", bm.getDifficultyrating()) + "☆   (" +
                                                    OsuMode.getByValue(bm.getMode()).getDescription() + ")", "CS: **" + bm.getDiff_size() + "** AR: **" + bm.getDiff_approach() +
                                                     "** OD: **" + bm.getDiff_overall() + "** HP: **" + bm.getDiff_drain() + "**\n Max combo: x" + bm.getMax_combo()
                                            , false);
                                }
                            }

                            if (beatmap.length > 1 && beatmap.length < 10) {
                                String diffs = "";
                                for (BeatmapJSON bm : beatmap) {
                                    if (bm.getBeatmap_id() != highest_id)
                                        diffs = diffs + bm.getVersion() + " (" + String.format("%.2f", bm.getDifficultyrating()) + "☆)  Mode: " + OsuMode.getByValue(bm.getMode()).getDescription() + "\n";
                                }

                                eb.addField("Other diffs", diffs, false);
                            }
                            System.out.println("Nuevo mapset");

                            //WHITELIST SERVERS

                            lab1:
                            for (BeatmapJSON bm : beatmap) {
                                if (bm.getMode() == 0) {

                                    //test server
                                    //jda.getGuildById("<serverid>").getDefaultChannel().sendMessage("New ranked map!").queue();
                                    //jda.getGuildById("<serverid>").getDefaultChannel().sendMessage(eb.build()).queue();
                                    break lab1;
                                }
                            }

                            ls.add(id);

                            Files.write(path, Arrays.asList(id), StandardCharsets.UTF_8,
                                    StandardOpenOption.APPEND);


                        } else {
                            System.out.println("Mapa salteado");
                        }
                    }

                }

//QUALIFIED MAPS(algun dia)
/*
                List<String> qls = Files.readAllLines(qpath, StandardCharsets.UTF_8);

                org.jsoup.nodes.Document doc = Jsoup.connect("https://osu.ppy.sh/beatmapsets?m=0&s=3/").get();

                org.jsoup.nodes.Element beatmapsJson = doc.getElementById("json-beatmaps");

                DataNode scriptContent = (DataNode) beatmapsJson.childNodes().get(0);

                String jsonDeMierda = scriptContent.getWholeData();

                JsonElement je = new JsonParser().parse(jsonDeMierda);

                JsonArray bmsets = je.getAsJsonObject().getAsJsonArray("beatmapsets");

                for (JsonElement bm : bmsets) {

                    JsonObject bmObject = bm.getAsJsonObject();

                    String id = bmObject.getAsJsonPrimitive("id").getAsString();

                    if (!qls.contains(id)) {

                        //Si la id no esta guardada
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle(bmObject.getAsJsonPrimitive("title").getAsString());
                        eb.setColor(Color.PINK);
                        eb.setThumbnail("http://b.ppy.sh/thumb/" + id + "l.jpg");

                        URL url = new URL("https://osu.ppy.sh/api/get_beatmaps?k=" + apikey + "&s=" + id);
                        HttpURLConnection request = (HttpURLConnection) url.openConnection();

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(request.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }

                        in.close();

                        BeatmapJSON[] beatmap = new Gson().fromJson(response.toString(), BeatmapJSON[].class);


                        eb.addField("BPM", "☆" + beatmap[0].getBpm(), true);


                        jda.getGuildById("583403918462156802").getDefaultChannel().sendMessage("New qualified map!").queue();
                        jda.getGuildById("583403918462156802").getDefaultChannel().sendMessage(eb.build()).queue();


                        Files.write(qpath, Arrays.asList(id), StandardCharsets.UTF_8,
                                StandardOpenOption.APPEND);

                    } else {
                        System.out.println("Mapa q salteado");

                    }
                }
*/


            System.out.println("Vamo a dormi");
            Thread.sleep(1000 * 120);
        }
            } catch (Exception e) {
            e.printStackTrace();
        }

    }



}





