package com.by5388.checkin;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @author Administrator  on 2020/1/10.
 */
public class SharedPreferencesTool {
    private static final String FILE_NAME = "check_in_name";
    private static final String LAST_NAME = "check_in_last_name";

    public synchronized static String getSavedName() {
        final CheckInApp instance = CheckInApp.getInstance();
        final SharedPreferences sharedPreferences = instance.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LAST_NAME, null);
    }


    public static synchronized void saveName(String name) {
        final CheckInApp instance = CheckInApp.getInstance();
        instance.execute(new Runnable() {
            @Override
            public void run() {
                final SharedPreferences sharedPreferences = instance.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                sharedPreferences.edit().putString(LAST_NAME, name).apply();
            }
        });

    }
}
