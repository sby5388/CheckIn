package com.by5388.checkin;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Administrator  on 2020/1/10.
 */
public class QueryResult {

    /**
     * msg : getDaKaData success
     * code : 0
     * data : [{"deviceType":"deviceType","crateDate":"2020-01-10 12:28:49","id":"id","userName":"userName"}]
     * count : 1
     */

    @SerializedName("msg")
    public String mMsg;
    @SerializedName("code")
    public int mCode;
    @SerializedName("count")
    public int mCount;
    @SerializedName("data")
    public List<CheckInItem> mData;

}
