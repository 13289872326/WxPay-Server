package com.xn.domain.user;
import java.io.Serializable;


/**
 * @Date 2018/12/25 14:17
 * @Author LHS
 * @ClassName UserEntity
 * @Description t_user表对应实体类
 */
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String  password;
    private String  nickName;
    private String  delFlag;
    private String  regDate;
    private String  phone;
    private String  email;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserEntity() {
    }

    public UserEntity(Long id, String userName, String password, String nickName, String delFlag, String regDate, String phone, String email, String remark) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.delFlag = delFlag;
        this.regDate = regDate;
        this.phone = phone;
        this.email = email;
        this.remark = remark;
    }

}
