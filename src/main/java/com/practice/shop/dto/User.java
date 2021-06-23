package com.practice.shop.dto;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/29 18:08
 * @Description:
 **/

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int u_id;
    private String name;
    private String password;
    private int sex;
    private String email;
    private String picpath;
    private Timestamp regTime;
    private boolean activeFlag;

    public User() {
    }

    public User(String u_email, String u_password) {
        this.password = u_password;
        this.email = u_email;
    }

    public User(int u_id, String name, String password, int sex, String email, String picpath, Timestamp regTime, boolean activeFlag) {
        this.u_id = u_id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.picpath = picpath;
        this.regTime = regTime;
        this.activeFlag = activeFlag;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
        this.regTime = regTime;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

}
