package devcows.com.rupibox_droid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

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

    private List<Plug> plugs;
    private LayoutInflater inflater = null;
    private PlugFragment fragment;

    public PlugAdapter(Context c, List<Plug> plugs, PlugFragment fragment) {
        Log.v(TAG, "Constructing CustomAdapter");

        this.plugs = plugs;
        this.fragment = fragment;
        inflater = LayoutInflater.from(c);
    }

    public void setPlugs(List<Plug> plugs) {
        this.plugs = plugs;
    }

    @Override
    public int getCount() {
        Log.v(TAG, "in getCount()");
        return plugs.size();
    }

    @Override
    public Object getItem(int position) {
        Log.v(TAG, "in getItem() for position " + position);
        return plugs.get(position);
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
        holder.sw_plug_value.setTag(plug);
        holder.sw_plug_value.setOnClickListener(fragment);

        // Setting all values in listview
        holder.txt_plug_name.setText(plug.getName());
        holder.txt_plug_pin.setText(plug.getPin_pi().toString());
        holder.sw_plug_value.setChecked(plug.getValue());

        return convertView;
    }
}
