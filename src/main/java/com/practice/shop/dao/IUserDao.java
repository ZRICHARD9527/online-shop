package com.practice.shop.dao;

import com.practice.shop.dto.Order;
import com.practice.shop.dto.User;

import java.util.List;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/31 21:05
 * @Description:
 **/

public interface IUserDao {
    //注册
    int register(User user);

    //登录
    Map<String, Object> login(User user);

    //获取用户详情
    User getById(int id);

    //添加购物车
    int addToCart(int u_id, int p_id, int num);

    //购买
    int purchase(int u_id, int p_id, int num, String address);

    //获取购物车
    List<Order> getCart(int u_id);

    //获取历史订单
    List<Order> getOrder(int u_id);
}
