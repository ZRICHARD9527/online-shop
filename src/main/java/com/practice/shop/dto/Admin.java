package com.practice.shop.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/8/2 22:03
 * @Description:
 **/
@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int a_id;
    private String name;
    private String password;
    private String email;

    public Admin(){}

    public Admin(int a_id, String name, String password, String email) {
        this.a_id = a_id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
