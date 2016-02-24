package socialbase.com.br.challengesocialbase.model;

import java.io.Serializable;

/**
 * Created by hortoni on 22/02/16.
 */
public class Image implements Serializable {

    private String guid;
    private String medium;

    public Image(){}

    public Image(String guid, String medium) {
        this.guid = guid;
        this.medium = medium;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
