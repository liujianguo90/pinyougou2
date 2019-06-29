package com.pinyougou.pojo.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private String nickName;
    private String profession;
    private String sex;
    private Date year;
    private Date month;
    private Date day;

    private String headPic;

    public User() {
    }



    public User(String nickName, String profession, String sex, Date year, Date month, Date day,String headPic) {
        this.nickName = nickName;
        this.profession = profession;
        this.sex = sex;
        this.year = year;
        this.month = month;
        this.day = day;
        this.headPic=headPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
}
