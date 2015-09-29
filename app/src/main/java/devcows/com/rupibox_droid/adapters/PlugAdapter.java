package devcows.com.rupibox_droid.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import devcows.com.rupibox_droid.views.PlugFragment;
import devcows.com.rupibox_droid.R;
import devcows.com.rupibox_droid.pojo.Plug;
import devcows.com.rupibox_droid.pojo.PlugList;

/**
 * Created by fox on 9/25/15.
 */
public class PlugAdapter extends BaseAdapter {
    static class ViewHolder {
        TextView txt_plug_name;
        TextView txt_plug_pin;
        Switch sw_plug_value;
    }

    private static final String TAG = "PlugAdapter";

    private LayoutInflater inflater = null;
    private PlugFragment fragment;

    public PlugAdapter(Context c, PlugFragment fragment) {
        Log.v(TAG, "Constructing CustomAdapter");

        this.fragment = fragment;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        Log.v(TAG, "in getCount()");
        return PlugList.getPlugs().size();
    }

    @Override
    public Object getItem(int position) {
        Log.v(TAG, "in getItem() for position " + position);
        return PlugList.getPlugs().get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.v(TAG, "in getItemId() for position " + position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Log.v(TAG, "in getView for position " + position + ", convertView is "
                + ((convertView == null) ? "null" : "being recycled"));

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_plug_item, null);
            holder = new ViewHolder();

            holder.txt_plug_name = (TextView) convertView
                    .findViewById(R.id.plug_name);
            holder.txt_plug_pin = (TextView) convertView
                    .findViewById(R.id.plug_pin);
            holder.sw_plug_value = (Switch) convertView.findViewById(R.id.plug_switch);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Plug plug = (Plug) getItem(position);
        plug.setAdapter(this);

        holder.sw_plug_value.setTag(plug);
        holder.sw_plug_value.setOnClickListener(fragment);

        // Setting all values in listview
        holder.txt_plug_name.setText(plug.getName());
        holder.txt_plug_pin.setText(plug.getPin_pi().toString());
        holder.sw_plug_value.setChecked(plug.getValue());

        return convertView;
    }
}
