package com.by5388.checkin;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

/**
 * @author Administrator  on 2020/1/10.
 */
public class CheckInApp extends Application implements Executor {

    private static CheckInApp sInstance;
    private Executor mExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutor = Executors.newFixedThreadPool(2);
        sInstance = this;
    }


    public static CheckInApp getInstance() {
        return sInstance;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mExecutor.execute(command);
    }
}
