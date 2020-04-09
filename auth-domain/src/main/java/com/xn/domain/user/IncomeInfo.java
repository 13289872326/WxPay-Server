package com.xn.domain.user;

import java.io.Serializable;
import java.util.Date;

public class IncomeInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    //收益者ID
    public String bfId;
    //贡献时间
    public Date profitTime;
    //贡献值
    public long profit;
    //贡献者ID
    public String ctbId;

    public IncomeInfo() {
    }

    @Override
    public String toString() {
        return "IncomeInfo{" +
                "id=" + id +
                ", bfId='" + bfId + '\'' +
                ", profitTime=" + profitTime +
                ", profit=" + profit +
                ", ctbId='" + ctbId + '\'' +
                '}';
    }

    public IncomeInfo(int id, String bfId, Date profitTime, long profit, String ctbId) {
        this.id = id;
        this.bfId = bfId;
        this.profitTime = profitTime;
        this.profit = profit;
        this.ctbId = ctbId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IncomeInfo(String bfId, Date profitTime, long profit, String ctbId) {
        this.bfId = bfId;
        this.profitTime = profitTime;
        this.profit = profit;
        this.ctbId = ctbId;
    }

    public String getBfId() {
        return bfId;
    }

    public void setBfId(String bfId) {
        this.bfId = bfId;
    }

    public Date getProfitTime() {
        return profitTime;
    }

    public void setProfitTime(Date profitTime) {
        this.profitTime = profitTime;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }

    public String getCtbId() {
        return ctbId;
    }

    public void setCtbId(String ctbId) {
        this.ctbId = ctbId;
    }

}
