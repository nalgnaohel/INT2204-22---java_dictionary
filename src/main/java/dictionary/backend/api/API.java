package dictionary.backend.api;

import java.io.IOException;

public interface API {
    void setAPIName();
    void work(String target, String langFrom, String langTo) throws Exception;
}
