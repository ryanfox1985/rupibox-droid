package devcows.com.rupibox_droid;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fox on 9/25/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Plug {
    private Integer id;
    private String name;
    private Integer pin_pi;
    private Boolean value;
    private String url;

    public Plug() {
    }

    public Plug(String name, Integer pin_pi, Boolean value) {
        this.name = name;
        this.pin_pi = pin_pi;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getPin_pi() {
        return pin_pi;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public JSONObject getJsonObject() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("id", this.id.toString());
        jsonObject.accumulate("value", this.value.toString());

        return jsonObject;
    }
}
