package com.xn.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 收益类
 */

public class Balance implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户ID
    public String userId;
    //收益更新时间
    public Date updateDate;
    //收益余额
    public long balan;
    //总收益额
    public long totalRevenue;

    @Override
    public String toString() {
        return "Balance{" +
                "userId='" + userId + '\'' +
                ", updateDate=" + updateDate +
                ", balan=" + balan +
                ", totalRevenue=" + totalRevenue +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public long getBalan() {
        return balan;
    }

    public void setBalan(long balan) {
        this.balan = balan;
    }

    public long getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(long totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Balance() {
    }

    public Balance(String userId, Date updateDate, long balan, long totalRevenue) {
        this.userId = userId;
        this.updateDate = updateDate;
        this.balan = balan;
        this.totalRevenue = totalRevenue;
    }
}
