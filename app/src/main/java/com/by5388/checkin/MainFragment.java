package com.by5388.checkin;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.by5388.checkin.databinding.FragmentMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import static java.util.Objects.requireNonNull;

/**
 * @author Administrator  on 2020/1/9.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private FragmentMainBinding mBinding;
    private CheckInTools mCheckInTools;
    private Handler mHandler = new Handler();
    private Toast mToast;

    private CheckInAdapter mAdapter;

    public CheckInAdapter getAdapter() {
        return mAdapter;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CheckInAdapter();
        mCheckInTools = new CheckInTools();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        mBinding.setFragment(this);
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.swipeRefreshLayout.setColorSchemeResources(
                R.color.green3,
                R.color.green6
        );
        mBinding.swipeRefreshLayout.setOnRefreshListener(this::loadData);
        mBinding.swipeRefreshLayout.setRefreshing(true);
        mBinding.buttonCheckIn.setOnClickListener(v -> postData());
        initData();
//        loadData();
    }

    private void initData() {
        CheckInApp.getInstance().execute(() -> {
            final String savedName = SharedPreferencesTool.getSavedName();
            if (!TextUtils.isEmpty(savedName)) {
                mHandler.post(() -> {
                    mBinding.editTextInputName.setText(savedName);
                    loadData();
                });
            }
        });
    }

    private void loadData() {
        mAdapter.setCheckInItems(new ArrayList<>());
        CheckInApp.getInstance().execute(() -> {
            try {
                final Editable text = mBinding.editTextInputName.getText();
                boolean empty = true;
                if (text != null) {
                    final String trim = text.toString().trim();
                    if (!TextUtils.isEmpty(trim)) {
                        mCheckInTools.loadData(trim);
                        empty = false;
                    }
                }
                if (empty) {
                    mCheckInTools.loadData();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            final List<CheckInItem> list = mCheckInTools.getList();
            mHandler.post(mRunnableFinish.setList(list));
        });
    }


    private LoadFinish mRunnableFinish = new LoadFinish();

    private class LoadFinish implements Runnable {
        private List<CheckInItem> list;

        LoadFinish setList(List<CheckInItem> list) {
            this.list = list;
            return this;
        }

        @UiThread
        @Override
        public void run() {
            if (list == null || list.isEmpty()) {
                mBinding.swipeRefreshLayout.setRefreshing(false);
                toast("没有找到数据");
                return;
            }
            mAdapter.setCheckInItems(list);
            mBinding.swipeRefreshLayout.setRefreshing(false);
            toast("刷新成功");
        }
    }

    private void postData() {
        final Editable text = mBinding.editTextInputName.getText();
        if (text == null || TextUtils.isEmpty(text.toString().trim())) {
            mBinding.textInputLayout.setError("请输入名字");
            return;
        }
        if (mBinding.textInputLayout.getError() != null) {
            mBinding.textInputLayout.setError("");
        }
        final String name = text.toString().trim();
        CheckInApp.getInstance().execute(() -> {
            try {
                SharedPreferencesTool.saveName(name);
                mCheckInTools.checkInNow(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final List<CheckInItem> list = mCheckInTools.getList();
            mHandler.post(mRunnableFinish.setList(list));
        });

    }

    private void toast(String s) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
        mToast.show();
    }


    private void showDeviceInformation() {

        //品牌
        Log.d(TAG, "postData:Build.BOARD = " + Build.BOARD);
        //TODO 制造商：华为
        Log.d(TAG, "postData:Build.DEVICE = " + Build.DEVICE);
        Log.d(TAG, "postData:Build.BOOTLOADER = " + Build.BOOTLOADER);
        //型号,显示？
        Log.d(TAG, "postData:Build.DISPLAY = " + Build.DISPLAY);
        //设备唯一识别码
        Log.d(TAG, "postData:Build.FINGERPRINT = " + Build.FINGERPRINT);
        //硬件？cpu
        Log.d(TAG, "postData:Build.HARDWARE = " + Build.HARDWARE);
        Log.d(TAG, "postData:Build.HOST = " + Build.HOST);
        //TODO 型号 ELE-AL00
        Log.d(TAG, "postData:Build.PRODUCT = " + Build.PRODUCT);
        Log.d(TAG, "postData:Build.MODEL = " + Build.MODEL);


        Log.d(TAG, "postData:Build.getRadioVersion = " + Build.getRadioVersion());

//        final TelephonyManager telephonyManager = (TelephonyManager) Objects.requireNonNull(getContext()).getSystemService(Context.TELEPHONY_SERVICE);
//        final String deviceId = telephonyManager.getDeviceId();

    }

}
