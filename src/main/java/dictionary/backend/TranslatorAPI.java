package dictionary.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TranslatorAPI implements API{
    private String apiName;
    private String res;
    @Override
    public void setAPIName() {
        apiName = "https://script.google.com/macros/s/AKfycby3AOWmhe32TgV9nW-Q0TyGOEyCHQeFiIn7hRgy5m8jHPaXDl2GdToyW_3Ys5MTbK6wjg/exec";
    }

    @Override
    public void work(String target, String langFrom, String langTo) throws Exception {
        res = translate(langFrom, langTo, target);
    }

    public String translate(String langFrom, String langTo, String text) throws IOException {
        setAPIName();
        String urlName = apiName + "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlName);
        StringBuilder res = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStreamReader isr = new InputStreamReader(con.getInputStream());
        BufferedReader in = new BufferedReader(isr);
        String line;
        while ((line = in.readLine()) != null) {
            res.append(line);
        }
        in.close();
        return res.toString();
    }

    public String getRes() {
        return res;
    }
}
