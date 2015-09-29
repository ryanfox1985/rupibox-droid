package devcows.com.rupibox_droid.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import devcows.com.rupibox_droid.pojo.PlugList;

/**
 * Created by fox on 9/25/15.
 */
public class PlugRequest extends SpringAndroidSpiceRequest<PlugList> {
    private String server_api_url;

    public PlugRequest(String server_api_url) {
        super(PlugList.class);

        this.server_api_url = server_api_url;
    }

    @Override
    public PlugList loadDataFromNetwork() throws Exception {
        String url = String.format("%s/pins.json", server_api_url);
        return getRestTemplate().getForObject(url, PlugList.class);
    }
}
