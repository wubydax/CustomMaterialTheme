package com.wubydax.custommaterialtheme;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;

import com.wubydax.custommaterialtheme.prefs.ColorPickerPreference;

/*      Created by Anna Berkovitch, 11/02/2016
        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.*/

@SuppressWarnings("deprecation")
public class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private OnFragmentInteractionListener mListener;
    SharedPreferences sp;
    SwitchPreference linkSwitch;
    ColorPickerPreference primaryColor, primaryDarkColor, accentColor;
    public static final String SWITCH_KEY = "link_colors";
    public static final String PRIMARY_COLOR_KEY = "primary_color";
    public static final String PRIMARY_DARK_COLOR_KEY = "primary_color_dark";
    public static final String ACCENT_COLOR_KEY = "accent_color";
    Utils utils;
    Resources res;

    public PrefsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        sp = getPreferenceManager().getSharedPreferences();
        linkSwitch = (SwitchPreference) findPreference(SWITCH_KEY);
        primaryColor = (ColorPickerPreference) findPreference(PRIMARY_COLOR_KEY);
        primaryDarkColor = (ColorPickerPreference) findPreference(PRIMARY_DARK_COLOR_KEY);
        accentColor = (ColorPickerPreference) findPreference(ACCENT_COLOR_KEY);
        utils = new Utils();
        res = getActivity().getResources();
//        mListener.displayToast();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mListener == null) {
            try {
                mListener = (OnFragmentInteractionListener) context;
                Toast.makeText(getActivity(), "Listener all set", Toast.LENGTH_SHORT).show();
            } catch (ClassCastException e) {
                Log.e("PrefsFragment", "onAttach Activity must implement the interface", e);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mListener == null) {
            try {
                mListener = (OnFragmentInteractionListener) activity;
            } catch (ClassCastException e) {
                Log.e("PrefsFragment", "onAttach Activity must implement the interface", e);
            }
        }
    }

    @Override
    public void onResume() {
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        setUpColors();
        super.onResume();
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setUpColors() {
        int primaryColorInt = sp.getInt(PRIMARY_COLOR_KEY, res.getColor(R.color.colorPrimary));
        int primaryDarkColorInt = sp.getInt(PRIMARY_DARK_COLOR_KEY, res.getColor(R.color.colorPrimaryDark));
        int accentColorInt = sp.getInt(ACCENT_COLOR_KEY, res.getColor(R.color.colorAccent));
        mListener.onPrimaryColorChanged(primaryColorInt);
        mListener.onPrimaryDarkColorChanged(primaryDarkColorInt);
        mListener.onAccentColorChanged(accentColorInt);
        primaryColor.setSummary(utils.getHexColor(primaryColorInt));
        primaryDarkColor.setSummary(utils.getHexColor(primaryDarkColorInt));
        accentColor.setSummary(utils.getHexColor(accentColorInt));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {

            case PRIMARY_COLOR_KEY:
                int primaryColorInt = sp.getInt(key, res.getColor(R.color.colorPrimary));
                if (!(sp.getBoolean(SWITCH_KEY, false))) {
                    int darkerColor = utils.getPrimaryDarkColor(primaryColorInt);
                    sp.edit().putInt(PRIMARY_DARK_COLOR_KEY, darkerColor).apply();
                    primaryDarkColor.onColorChanged(darkerColor);
                    mListener.onPrimaryDarkColorChanged(darkerColor);
                }
                primaryColor.setSummary(utils.getHexColor(primaryColorInt));
                mListener.onPrimaryColorChanged(primaryColorInt);
                break;
            case PRIMARY_DARK_COLOR_KEY:
                int primaryDarkColorInt = sp.getInt(key, res.getColor(R.color.colorPrimaryDark));
                primaryDarkColor.setSummary(utils.getHexColor(primaryDarkColorInt));
                mListener.onPrimaryDarkColorChanged(primaryDarkColorInt);
                break;
            case ACCENT_COLOR_KEY:
                int accentColorInt = sp.getInt(key, res.getColor(R.color.colorAccent));
                accentColor.setSummary(utils.getHexColor(accentColorInt));
                mListener.onAccentColorChanged(accentColorInt);
                break;
        }

    }


    public interface OnFragmentInteractionListener {
        void onPrimaryColorChanged(int color);

        void onPrimaryDarkColorChanged(int color);

        void onAccentColorChanged(int color);

    }
}
