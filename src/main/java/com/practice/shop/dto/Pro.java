package com.practice.shop.dto;


import javax.persistence.*;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/29 18:08
 * @Description:
 **/

/**
 * 字段名称	数据类型	默认值	允许非空	自动递增	备注
 * p_id	int		NO	YES	主键
 * name	varchar(30)		NO		商品名称
 * c_id	int		NO		所属分类id
 * sn	varchar(50)		NO		商品货号
 * num	int	0	NO		商品库存
 * price	double		NO		价格
 * desc	varchar(255)		YES		商品简介
 * time	datetime		NO		上架时间
 */

@Entity
@Data
public class Pro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int p_id;
    private String name;
    private String sn;
    private int num;
    private double price;
    private int sales;
    private String desc;
    private Timestamp time;
    @Transient
    private List<String> picpath;
    @Transient
    private Integer c_id;


    public Pro() {
    }


    public Pro(int p_id, String name, double price, String desc, List<String> picpath, int sales) {
        this.p_id = p_id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.picpath = picpath;
        this.sales = sales;
    }

    public Pro(int p_id, String name, String sn, int num, double price, String desc, Timestamp time, List<String> picpath, int sales) {
        this.p_id = p_id;
        this.name = name;
        this.sn = sn;
        this.num = num;
        this.price = price;
        this.desc = desc;
        this.time = time;
        this.picpath = picpath;
        this.sales = sales;
    }


    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public List<String> getPicpath() {
        return picpath;
    }

    public void setPicpath(List<String> picpath) {
        this.picpath = picpath;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }
}
