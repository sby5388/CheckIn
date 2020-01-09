package com.by5388.checkin;

import androidx.fragment.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }
}
