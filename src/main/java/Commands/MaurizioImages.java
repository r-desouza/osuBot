package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Random;

public class MaurizioImages extends ListenerAdapter {

    int m;

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] message = e.getMessage().getContentRaw().split(" ");

        if(message.length == 1 && message[0].equalsIgnoreCase("%mosca")) {


            EmbedBuilder eb = new EmbedBuilder();
            SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date date = new Date();


            eb.setTitle("Maurizio siendo peruano");
            eb.setColor(Color.PINK);
            eb.setFooter("Request made at " + datef.format(date), e.getGuild().getIconUrl());


            Random rand = new Random();

            int n;

            do{
                n = rand.nextInt(13) + 1;
            }while (n==m);

            switch(n) {
                case 1:
                    eb.setImage("https://i.imgur.com/4f5erl4.png");
                break;
                case 2:
                    eb.setImage("https://i.imgur.com/NTkMXpL.png");
                break;
                case 3:
                    eb.setImage("https://i.imgur.com/sGduzo4.png");
                    break;
                case 4:
                    eb.setImage("https://i.imgur.com/sGduzo4.png");
                    break;
                case 5:
                    eb.setImage("https://i.imgur.com/ZLt4qYy.png");
                    break;
                case 6:
                    eb.setImage("https://i.imgur.com/2qmnX80.png");
                    break;
                case 7:
                    eb.setImage("https://i.imgur.com/SYYR0RX.png");
                    break;
                case 8:
                    eb.setImage("https://i.imgur.com/gVDMlxe.png");
                    break;
                case 9:
                    eb.setImage("https://i.imgur.com/jKP7QKM.png");
                    break;
                case 10:
                    eb.setImage("https://i.imgur.com/5U2MUts.png");
                    break;
                case 11:
                    eb.setImage("https://i.imgur.com/aSvXYfJ.png");
                    break;
                case 12:
                    eb.setImage("https://i.imgur.com/aaikMj0.png");
                    break;
                case 13:
                    eb.setImage("https://i.imgur.com/DzFQCkX.png");
                    break;
                    default:
            }

            m = n;
            e.getChannel().sendMessage(eb.build()).queue();

        } else if (message.length >= 2 && message[0].equalsIgnoreCase("%mosca")) {
            e.getChannel().sendMessage("use %mosca to see random peruvian images!").queue();
        }

    }



}
