package com.py.entity;

import java.util.Date;

public class User {
    private Integer id;

    private String phone;

    private String name;

    private String salt;

    private String passWord;

    private String idCode;

    private String headUrl;

    private Date regDate;

    private Date lastLoginTime;

    private String lastLoginLongitude;

    private String lastLoginLatitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginLongitude() {
        return lastLoginLongitude;
    }

    public void setLastLoginLongitude(String lastLoginLongitude) {
        this.lastLoginLongitude = lastLoginLongitude;
    }

    public String getLastLoginLatitude() {
        return lastLoginLatitude;
    }

    public void setLastLoginLatitude(String lastLoginLatitude) {
        this.lastLoginLatitude = lastLoginLatitude;
    }
}