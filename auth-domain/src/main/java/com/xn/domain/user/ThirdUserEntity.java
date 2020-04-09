package com.xn.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @Date 2018/12/25 14:22
 * @Author LHS
 * @ClassName ThirdUserEntity
 * @Description :...
 */
public class ThirdUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private String thirdUserId;
    private int plateformType;
    private String companyId;
    private String serverIp;
    private String serverPort;
    private String thirdUserName;
    private String thirdPassword;
    private String thirdToken;
    private Date authExpire;
    private Date authTime;
    private boolean isUsed;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPlateformType() {
        return plateformType;
    }

    public void setPlateformType(int plateformType) {
        this.plateformType = plateformType;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getThirdUserName() {
        return thirdUserName;
    }

    public void setThirdUserName(String thirdUserName) {
        this.thirdUserName = thirdUserName;
    }

    public String getThirdPassword() {
        return thirdPassword;
    }

    public void setThirdPassword(String thirdPassword) {
        this.thirdPassword = thirdPassword;
    }

    public String getThirdToken() {
        return thirdToken;
    }

    public void setThirdToken(String thirdToken) {
        this.thirdToken = thirdToken;
    }

    public Date getAuthExpire() {
        return authExpire;
    }

    public void setAuthExpire(Date authExpire) {
        this.authExpire = authExpire;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
    }


    public ThirdUserEntity() {
    }

    public ThirdUserEntity(Long id, Long userId, String thirdUserId, int plateformType, String companyId, String serverIp, String serverPort, String thirdUserName, String thirdPassword, String thirdToken, Date authExpire, Date authTime, boolean isUsed) {
        this.id = id;
        this.userId = userId;
        this.thirdUserId = thirdUserId;
        this.plateformType = plateformType;
        this.companyId = companyId;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.thirdUserName = thirdUserName;
        this.thirdPassword = thirdPassword;
        this.thirdToken = thirdToken;
        this.authExpire = authExpire;
        this.authTime = authTime;
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return "ThirdUserEntity{" +
                "id=" + id +
                ", plateformType='" + plateformType + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", thirdUserName='" + thirdUserName + '\'' +
                ", thirdPassword='" + thirdPassword + '\'' +
                ", thirdToken='" + thirdToken + '\'' +
                ", authExpire=" + authExpire +
                ", authTime=" + authTime +
                ", userId=" + userId +
                ", isUsed=" + isUsed +
                ", thirdUserId='" + thirdUserId + '\'' +
                '}';
    }


}
