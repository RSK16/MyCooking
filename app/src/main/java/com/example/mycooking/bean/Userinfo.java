package com.example.mycooking.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/9/4.
 */

//注册信息
public class Userinfo extends BmobUser{

    private String icon_url;
    private String nickName;

    //当为true代表男性，false代表性别为女
    private Boolean sex;
    private String address;
    private String job;
    private String birth;
    private String collect;

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
