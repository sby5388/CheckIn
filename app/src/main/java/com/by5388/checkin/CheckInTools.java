package com.by5388.checkin;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

/**
 * @author Administrator  on 2020/1/10.
 */
public class CheckInTools {
    private static final String BASE_URL = "http://106.12.208.156:8080/bns/";
    private static final String TAG = "CheckInTools";
    private List<CheckInItem> list;
    private Gson mGson;

    public CheckInTools() {
        list = new ArrayList<>();
        mGson = new Gson();
    }


    public List<CheckInItem> getList() {
        return list;
    }

    @WorkerThread
    List<CheckInItem> loadData() throws IOException {
        list.clear();
        loadDataFromServer();
        return list;
    }

    @WorkerThread
    List<CheckInItem> loadData(String key) throws IOException {
        list.clear();
        loadDataFromServer(key);
        return list;
    }

    void loadDataFromServer(@Nullable String name) throws IOException {
        final Uri.Builder builder = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("getDaKaData.do");
        if (!TextUtils.isEmpty(name)) {
            builder.appendQueryParameter("userName", name);
        } else {
            builder.appendQueryParameter("userName", "");
        }
        final Uri uri = builder.build();
        final String urlString = uri.toString();
        Log.d(TAG, "loadDataFromServer: urlString = " + urlString);
        final String result = getResult(uri);
        final QueryResult queryResult = mGson.fromJson(result, QueryResult.class);
        if (queryResult != null) {
            list.clear();
            list.addAll(queryResult.mData);
        }

    }

    void loadDataFromServer() throws IOException {
        loadDataFromServer(null);
    }


    void checkInNow(@NonNull String name) throws IOException {
        addCheckIn(name);
    }


    void addCheckIn(String name) throws IOException {
        final String deviceName = Build.DEVICE + " - " + Build.PRODUCT;
        final Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("daKa.do")
                .appendQueryParameter("userName", name)
                .appendQueryParameter("deviceType", deviceName)
                .build();
        final String urlString = uri.toString();
        Log.d(TAG, "addCheckIn: urlString = " + urlString);
        final String result = getResult(uri);

        final PostResult postResult = mGson.fromJson(result, PostResult.class);
        // TODO: 2020/1/10
        if (postResult != null) {
            list.add(0, postResult.mData);
        }
    }

    private String getResult(final Uri uri) throws IOException {
        final URL url = new URL(uri.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        final int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("error response code");
        }
        final InputStream inputStream = connection.getInputStream();
        final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] buffer = new byte[1024];
        while ((length = inputStream.read(buffer)) > 0) {
            arrayOutputStream.write(buffer, 0, length);
        }
        final byte[] result = arrayOutputStream.toByteArray();
        arrayOutputStream.close();
        inputStream.close();
        connection.disconnect();
        final String resultString = new String(result);
        Log.d(TAG, "getResult: result = " + resultString);
        return resultString;
    }


}
