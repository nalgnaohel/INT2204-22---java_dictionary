package dictionary.backend;

import javazoom.jl.player.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleAPI extends API{
    //API cua GoogleTranslate, dung de doc tu/cau.

    @Override
    public void setAPIName() {
        apiName = "https://translate.google.com/translate_tts?ie=UTF-8&tl=" +
                "en" + "&client=tw-ob&q=";
    }

    /**
     * Get speech of a text.
     */
    public void playWordSound(String text) {
        try {
            setAPIName();
            String urlName = apiName + URLEncoder.encode(text, StandardCharsets.UTF_8);
            System.out.println(urlName);
            URL url = new URL(urlName);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            InputStream audioSource = con.getInputStream();
            Player player = new Player(audioSource);
            player.play();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occured while playing voices!");
        }

    }
}
