package com.practice.shop.dto;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/31 17:16
 * @Description:
 **/
@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int o_id;
    private int u_id;
    private int p_id;
    @Transient
    private Pro pro;//商品
    private boolean isbuy;
    private String address;
    private Timestamp time;
    private Integer num;

    public Order() {
    }

    public Order(int o_id, int u_id, int p_id, Pro pro, boolean isbuy, String address) {
        this.o_id = o_id;
        this.u_id = u_id;
        this.p_id = p_id;
        this.pro = pro;
        this.isbuy = isbuy;
        this.address = address;
    }

    public int getO_id() {
        return o_id;
    }

    public void setO_id(int o_id) {
        this.o_id = o_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public Pro getPro() {
        return pro;
    }

    public void setPro(Pro pro) {
        this.pro = pro;
    }

    public boolean isIsbuy() {
        return isbuy;
    }

    public void setIsbuy(boolean isbuy) {
        this.isbuy = isbuy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
