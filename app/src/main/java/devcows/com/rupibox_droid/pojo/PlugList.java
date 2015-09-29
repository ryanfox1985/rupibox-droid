package devcows.com.rupibox_droid.pojo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import devcows.com.rupibox_droid.adapters.PlugAdapter;

/**
 * Created by fox on 9/25/15.
 */
public class PlugList extends ArrayList<Plug> {
    private static List<Plug> mPlugs = new ArrayList<>();
    private static PlugAdapter mAdapter;
    private static Context mContext;

    public static List<Plug> getPlugs() {
        return mPlugs;
    }

    public static void setPlugs(List<Plug> plugs) {
        PlugList.mPlugs = plugs;

        if (mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

    public static void setAdapter(PlugAdapter adapter){
        mAdapter = adapter;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }
}
