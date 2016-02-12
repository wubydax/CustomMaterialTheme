package com.wubydax.custommaterialtheme;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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


public class MainActivity extends AppCompatActivity implements PrefsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PrefsFragment()).commit();
        }
    }


    @Override
    public void onPrimaryColorChanged(int color) {
        findViewById(R.id.appTitleText).setBackgroundColor(color);

    }

    @Override
    public void onPrimaryDarkColorChanged(int color) {
        findViewById(R.id.statusBar).setBackgroundColor(color);

    }

    @Override
    public void onAccentColorChanged(int color) {
        ((TextView) findViewById(R.id.accentColotText)).setTextColor(color);
        int[][] states = new int[][]{new int[]{-android.R.attr.state_pressed}};
        int[] colors = new int[]{color};
        ColorStateList csl = new ColorStateList(states, colors);
        findViewById(R.id.fab).setBackgroundTintList(csl);

    }


}
