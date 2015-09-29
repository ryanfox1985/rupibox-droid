package devcows.com.rupibox_droid.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import devcows.com.rupibox_droid.MainActivity;
import devcows.com.rupibox_droid.R;
import devcows.com.rupibox_droid.adapters.PlugAdapter;
import devcows.com.rupibox_droid.pojo.PlugList;
import devcows.com.rupibox_droid.pojo.Plug;
import devcows.com.rupibox_droid.requests.PlugRequest;

/**
 * Created by fox on 9/25/15.
 */
public class PlugFragment extends MainActivity.PlaceholderFragment implements RequestListener<PlugList>, View.OnClickListener {
    private PlugAdapter mAdapter;

    private TextView mTxtLoading;
    private ListView mListView;
    private String mServerApiUrl;

    private static final String TAG = "PlugFragment";

    public void refresh_plugs(){
        mListView.setVisibility(View.INVISIBLE);
        mTxtLoading.setVisibility(View.VISIBLE);
        mTxtLoading.setText("Loading plugs...");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(PlugList.getContext());
        mServerApiUrl = sharedPref.getString("server_api_url", null);

        if (mServerApiUrl != null) {
            PlugRequest request = new PlugRequest(mServerApiUrl);
            mSpiceManager.execute(request, "", DurationInMillis.ONE_MINUTE, this);
        } else {
            Toast.makeText(PlugList.getContext(), "Please set server API URL!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get ListView object from xml
        mListView = (ListView) rootView.findViewById(R.id.plug_list);

        mAdapter = new PlugAdapter(PlugList.getContext(), this);
        mListView.setAdapter(mAdapter);
        PlugList.setAdapter(mAdapter);

        mTxtLoading = (TextView) rootView.findViewById(R.id.txt_loading);

        refresh_plugs();
        return rootView;
    }


    @Override
    public void onRequestFailure(SpiceException spiceException) {
        mTxtLoading.setText("Error Robospice fail..." + spiceException.getMessage());

        mListView.setVisibility(View.INVISIBLE);
        mTxtLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestSuccess(PlugList plugs) {
        mTxtLoading.setVisibility(View.INVISIBLE);

        PlugList.setPlugs(plugs);
        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        Plug plug = (Plug) v.getTag();
        Log.v(TAG, "POST plug: " + plug.toString());

        plug.setValue(((Switch) v).isChecked());
    }
}
