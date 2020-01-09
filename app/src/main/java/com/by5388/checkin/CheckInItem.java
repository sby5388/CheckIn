package com.by5388.checkin;

/**
 * @author Administrator  on 2020/1/9.
 */
public class CheckInItem {
    public int mId;
    public String mName;
    public long mTime;
    public String mDevice;

    public CheckInItem(int id, String name, long time, String device) {
        mId = id;
        mName = name;
        mTime = time;
        mDevice = device;
    }
}
