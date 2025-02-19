package dictionary.backend.api;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class DictAPI implements API {
    //Dictionary API, dung de tim synonyms, antonyms.
    Map<String, ArrayList<String>> mp = new TreeMap<String, ArrayList<String>>();
    private String apiName;
    private String res;
    public void setAPIName() {
        apiName = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    }

    public void work(String target, String langFrom, String langTo) throws IOException {
        res = getNeededInfo(target);
    }
    /**
     * Find synonyms, antonyms and examples.
     */
    private void findAll(String response, String target) {
        int lastSIndex = 0;
        mp.put(target, new ArrayList<String>());
        while (lastSIndex != -1) {
            lastSIndex = response.indexOf(target, lastSIndex);
            if (lastSIndex != -1) {
                int i = lastSIndex;
                while(response.charAt(i) != '[') {
                    i++;
                }
                //System.out.println(response.charAt(i));
                String targetWord = "";
                while (response.charAt(i) != ']') {
                    if (response.charAt(i) == '"') {
                        targetWord += '"'; i++;
                        while (response.charAt(i) != '"') {
                            targetWord += response.charAt(i);
                            i++;
                        }
                        targetWord += '"';
                        if (!mp.get(target).contains(targetWord)) {
                            mp.get(target).add(targetWord);
                        }
                        targetWord = "";
                    }
                    i++;
                }
                lastSIndex += target.length();
            }
        }
    }

    public String getNeededInfo(String target) throws IOException {
        setAPIName();
        String urlName = apiName + target;
        URL url = new URL(urlName);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        StringBuilder res = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String responseLine;
        while ((responseLine = in.readLine()) != null) {
            String cur = responseLine.trim();
            res.append(cur);
        }

        String response = res.toString();

        //Find synonym
        findAll(response, "synonyms");
        findAll(response, "antonyms");
        //findAll(response, "examples");
        String result = "";
        for (String key : mp.keySet()) {
            result += key + ": ";
            if (!mp.get(key).isEmpty()) {
                for (int i = 0; i < mp.get(key).size() - 1; i++) {
                    result += mp.get(key).get(i) + ", ";
                }
                result += mp.get(key).get(mp.get(key).size() - 1);
            }
            result += "\n";
        }
        //result tra ve cac antonyms va synonyms
        return result;
    }

    public String getRes() {
        return res;
    }
}
