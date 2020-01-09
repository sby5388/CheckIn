package com.by5388.checkin;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * @author Administrator  on 2020/1/9.
 */
public class CheckInViewModel extends BaseObservable {
    private final CheckInItem mCheckInItem;

    public CheckInViewModel(CheckInItem checkInItem) {
        mCheckInItem = checkInItem;
    }


    @Bindable
    public String getName() {
        return mCheckInItem.mName;
    }

    @Bindable
    public String getTime() {
        // TODO: 2020/1/9
        return String.valueOf(mCheckInItem.mTime);
    }

    @Bindable
    public String getDevice() {
        return mCheckInItem.mDevice;
    }
}
