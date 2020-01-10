package com.by5388.checkin;

import com.google.gson.annotations.SerializedName;

/**
 * @author Administrator  on 2020/1/10.
 */
public class PostResult {


    /**
     * msg : data submit success
     * code : 1
     * data : {"deviceType":"android","userName":"test","createDate":"2020-01-10 11:56:14"}
     * count : 0
     */

    @SerializedName("msg")
    public String mMsg;
    @SerializedName("code")
    public int mCode;
    @SerializedName("data")
    public CheckInItem mData;
    @SerializedName("count")
    public int mCount;
    
}
