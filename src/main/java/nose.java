import com.oopsjpeg.osu4j.GameMode;
import com.oopsjpeg.osu4j.OsuBeatmap;
import com.oopsjpeg.osu4j.OsuScore;
import com.oopsjpeg.osu4j.OsuUser;
import com.oopsjpeg.osu4j.backend.EndpointUsers;
import com.oopsjpeg.osu4j.backend.Osu;
import com.oopsjpeg.osu4j.exception.OsuAPIException;

import java.net.MalformedURLException;
import java.util.List;

public class nose {
        private static final String USER = "pein";
        private static final GameMode MODE = GameMode.STANDARD;
        private static final int TOP_SCORE_LIMIT = 15;

        public static void main(String[] args) throws OsuAPIException, MalformedURLException {
            // Create a new Osu object with an API key
            Osu osu = Osu.getAPI("424aa3a77eef50a8105cedb68cbb4609f50ea6fe");

            // Get the user $user pein
            System.out.println("Getting user...");
            OsuUser user = osu.users.query(new EndpointUsers.ArgumentsBuilder(USER).setMode(MODE).build());

            // Print some user info
            System.out.println(user.getUsername() + "(" + user.getID() + ")");
            System.out.println(user.getURL());
            System.out.println("Rank: #" + user.getRank());
            System.out.println("Performance: #" + user.getRank() + " (" + user.getPP() + "pp)");
            System.out.println("Total Score: " + user.getTotalScore());

            // Get the user's top scores
            List<OsuScore> topScores = user.getTopScores(TOP_SCORE_LIMIT).get();

            // Print the top scores
            for (int i = 0; i < topScores.size(); i++) {
                OsuScore score = topScores.get(i);
                OsuBeatmap beatmap = score.getBeatmap().get();
                System.out.println("Top score #" + (i + 1) + ": " + beatmap.toString() + " " + beatmap.getURL());
            }



        }
    }






