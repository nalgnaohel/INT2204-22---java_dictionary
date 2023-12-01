package dictionary.backend;

import java.io.IOException;

public interface API {
    void setAPIName();
    public void work(String target, String langFrom, String langTo) throws Exception;
}
