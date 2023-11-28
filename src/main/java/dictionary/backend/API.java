package dictionary.backend;

import java.io.IOException;

public abstract class API {
    public abstract void setAPIName();

    public String getNeededInfo(String target) throws IOException {
        if (this instanceof DictAPI) {
            return ((DictAPI) this).getNeededInfo(target);
        }
        return "";
    }

    public void playWordSound(String target, String fromLang) {

    }

    public String translate(String langFrom, String langTo, String text) throws IOException {
        if (this instanceof TranslatorAPI) {
            return ((TranslatorAPI) this).translate(langFrom, langTo, text);
        }
        return "";
    }
}
