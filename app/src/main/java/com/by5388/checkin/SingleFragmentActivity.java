package com.by5388.checkin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author Administrator  on 2020/1/9.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = createFragment();
            supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit();
        }

    }
}
