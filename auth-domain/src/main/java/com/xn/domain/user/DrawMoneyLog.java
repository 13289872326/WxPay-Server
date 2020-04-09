package com.xn.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户提现记录
 */
public class DrawMoneyLog  implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户ID
    public String userId;
    //收益更新时间
    public Date logDate;
    //收益余额
    public long moneybalan;
    //提现金额
    public long totalRevenue;
    //申请时间
    public Date askDate;
    //到账时间
    public Date arrDate;
    //申请状态
    public int executState;

    public DrawMoneyLog(String userId, Date logDate, long moneybalan, long totalRevenue, Date askDate, Date arrDate, int executState) {
        this.userId = userId;
        this.logDate = logDate;
        this.moneybalan = moneybalan;
        this.totalRevenue = totalRevenue;
        this.askDate = askDate;
        this.arrDate = arrDate;
        this.executState = executState;
    }

    public int getExecutState() {
        return executState;
    }

    public void setExecutState(int executState) {
        this.executState = executState;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public long getMoneybalan() {
        return moneybalan;
    }

    public void setMoneybalan(long moneybalan) {
        this.moneybalan = moneybalan;
    }

    public long getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(long totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Date getAskDate() {
        return askDate;
    }

    public void setAskDate(Date askDate) {
        this.askDate = askDate;
    }

    public Date getArrDate() {
        return arrDate;
    }

    public void setArrDate(Date arrDate) {
        this.arrDate = arrDate;
    }

    public DrawMoneyLog() {
    }

    public DrawMoneyLog(String userId, Date logDate, long moneybalan, long totalRevenue, Date askDate, Date arrDate) {
        this.userId = userId;
        this.logDate = logDate;
        this.moneybalan = moneybalan;
        this.totalRevenue = totalRevenue;
        this.askDate = askDate;
        this.arrDate = arrDate;
    }
}
