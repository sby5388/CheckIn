package com.by5388.checkin;

import com.google.gson.annotations.SerializedName;

/**
 * @author Administrator  on 2020/1/9.
 */
public class CheckInItem {
    //    @SerializedName("id")
//    public int mId = 1;
    @SerializedName("userName")
    public String mName;
    @SerializedName(value = "createDate", alternate = {"crateDate"})
    public String mTime;
    @SerializedName("deviceType")
    public String mDevice;

}
