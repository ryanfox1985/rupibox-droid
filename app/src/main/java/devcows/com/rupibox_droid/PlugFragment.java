package devcows.com.rupibox_droid;

import android.content.Context;
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

import java.util.ArrayList;

/**
 * Created by fox on 9/25/15.
 */
public class PlugFragment extends MainActivity.PlaceholderFragment implements RequestListener<PlugList>, View.OnClickListener {
    private PlugAdapter mAdapter;

    private TextView mTxtLoading;
    private ListView mListView;
    private Context mContext;
    private String mServerApiUrl;

    private static final String TAG = "PlugFragment";

    public PlugFragment(Context context) {
        this.mContext = context;
    }

    public void refresh_plugs(){
        mListView.setVisibility(View.INVISIBLE);
        mTxtLoading.setVisibility(View.VISIBLE);
        mTxtLoading.setText("Loading plugs...");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        mServerApiUrl = sharedPref.getString("server_api_url", null);

        if (mServerApiUrl != null) {
            MainActivity.plugs = new ArrayList<>();

            PlugRequest request = new PlugRequest(mServerApiUrl);
            mSpiceManager.execute(request, "", DurationInMillis.ONE_MINUTE, this);
        } else {
            Toast.makeText(mContext, "Please set server API URL!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get ListView object from xml
        mListView = (ListView) rootView.findViewById(R.id.plug_list);

        mAdapter = new PlugAdapter(mContext, new PlugList(), this);
        mListView.setAdapter(mAdapter);

        mTxtLoading = (TextView) rootView.findViewById(R.id.txt_loading);

        refresh_plugs();

        return rootView;
    }


    @Override
    public void onRequestFailure(SpiceException spiceException) {
        mListView.setVisibility(View.INVISIBLE);

        mTxtLoading.setVisibility(View.VISIBLE);
        mTxtLoading.setText("Error Robospice fail..." + spiceException.getMessage());
    }

    @Override
    public void onRequestSuccess(PlugList plugs) {
        mTxtLoading.setVisibility(View.INVISIBLE);

        mAdapter.setPlugs(plugs);
        mAdapter.notifyDataSetChanged();

        mListView.setVisibility(View.VISIBLE);
        MainActivity.plugs = plugs;
    }

    @Override
    public void onClick(View v) {
        Plug plug = (Plug) v.getTag();
        plug.setValue(((Switch) v).isChecked());
        Log.v(TAG, "POST plug: " + plug.toString());

        new PlugPostTask(mContext, mServerApiUrl).execute(plug);
    }
}
