package dictionary.backend;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DictAPI {
    public String getNeededInfo(String target) throws IOException {
        String urlName = "https://api.dictionaryapi.dev/api/v2/entries/en/" + target;
        URL url = new URL(urlName);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        StringBuilder result = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String responseLine;
        int id = 0;
        while ((responseLine = in.readLine()) != null) {
            id++;
            System.out.println(id);
            String cur = responseLine.trim();
            result.append(cur);
        }
        //int iSym = responseLine.lastIndexOf("synnonym");
        return result.toString();
    }
}
