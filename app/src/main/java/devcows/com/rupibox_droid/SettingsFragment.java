package devcows.com.rupibox_droid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.github.machinarius.preferencefragment.PreferenceFragment;

/**
 * Created by fox on 9/25/15.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

        final Preference pref = getPreferenceManager().findPreference("server_api_url");
        pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                pref.setSummary(newValue.toString());
                return true;
            }
        });

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String server_api_url = sharedPref.getString("server_api_url", null);
        if (server_api_url != null){
            pref.setSummary(server_api_url);
        }
    }

}
