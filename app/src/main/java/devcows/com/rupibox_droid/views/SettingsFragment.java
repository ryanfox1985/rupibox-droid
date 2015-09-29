package devcows.com.rupibox_droid.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import com.github.machinarius.preferencefragment.PreferenceFragment;

import devcows.com.rupibox_droid.R;

/**
 * Created by fox on 9/25/15.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

        final Preference pref_server = getPreferenceManager().findPreference("server_api_url");
        pref_server.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                pref_server.setSummary(newValue.toString());
                return true;
            }
        });

        final Preference pref_distance = getPreferenceManager().findPreference("levenshtein_distance");
        pref_distance.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                pref_distance.setSummary(newValue.toString());
                return true;
            }
        });

        Integer levenshtein_distance = getLevenshteinDistance(getActivity());
        pref_distance.setSummary(levenshtein_distance.toString());
    }


    public static Integer getLevenshteinDistance(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Integer levenshtein_distance = 2;

        try {
            levenshtein_distance = Integer.parseInt(sharedPref.getString("levenshtein_distance", "2"));
        } catch (Exception e){
            Log.v("SettingsFragment", e.getMessage());
        }

        return levenshtein_distance;
    }
}
