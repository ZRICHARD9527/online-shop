package com.practice.shop.dao.impl;

import com.practice.shop.dao.IAdminDao;

import static org.junit.jupiter.api.Assertions.*;

class IAdminDaoImplTest {
    public static void main(String[] args) {
        IAdminDao iAdminDao=new IAdminDaoImpl();
        System.out.println(iAdminDao.getProPage(0,5));
    }
}