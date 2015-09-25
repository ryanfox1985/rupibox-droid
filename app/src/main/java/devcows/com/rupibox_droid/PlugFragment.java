package devcows.com.rupibox_droid;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by fox on 9/25/15.
 */
public class PlugFragment extends MainActivity.PlaceholderFragment implements RequestListener<PlugList>, View.OnClickListener {
    private PlugAdapter mAdapter;

    private TextView mTxtLoading;
    private ListView mListView;

    private static final String TAG = "PlugFragment";

    public PlugFragment() {
        PlugRequest request = new PlugRequest();
        mSpiceManager.execute(request, "", DurationInMillis.ONE_MINUTE, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get ListView object from xml
        mListView = (ListView) rootView.findViewById(R.id.plug_list);
        mListView.setVisibility(View.INVISIBLE);

        mAdapter = new PlugAdapter(getActivity().getBaseContext(), new PlugList(), this);
        mListView.setAdapter(mAdapter);

        mTxtLoading = (TextView) rootView.findViewById(R.id.txt_loading);
        mTxtLoading.setVisibility(View.VISIBLE);
        mTxtLoading.setText("Loading plugs...");

        return rootView;
    }


    @Override
    public void onRequestFailure(SpiceException spiceException) {
        mListView.setVisibility(View.INVISIBLE);

        mTxtLoading.setVisibility(View.VISIBLE);
        mTxtLoading.setText("Error Robospice fail...");
    }

    @Override
    public void onRequestSuccess(PlugList plugs) {
        mTxtLoading.setVisibility(View.INVISIBLE);

        mAdapter.setPlugs(plugs);
        mAdapter.notifyDataSetChanged();

        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        Plug plug = (Plug) v.getTag();
        plug.setValue(((Switch) v).isChecked());
        Log.v(TAG, "POST plug: " + plug.toString());

        new PlugPostTask(getActivity().getBaseContext()).execute(plug);
    }
}
