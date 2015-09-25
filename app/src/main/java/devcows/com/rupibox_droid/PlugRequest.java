package devcows.com.rupibox_droid;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

/**
 * Created by fox on 9/25/15.
 */
public class PlugRequest extends SpringAndroidSpiceRequest<PlugList> {

    public PlugRequest() {
        super(PlugList.class);
    }

    @Override
    public PlugList loadDataFromNetwork() throws Exception {
        String url = String.format("http://10.10.10.104:3000/pins.json");
        return getRestTemplate().getForObject(url, PlugList.class);
    }
}
