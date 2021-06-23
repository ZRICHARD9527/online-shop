package com.practice.shop.dao;

import com.practice.shop.dto.Order;
import com.practice.shop.dto.Pro;
import com.practice.shop.dto.User;

import java.util.List;
import java.util.Map;

public interface IAdminDao {
    //通过邮箱和密码登录
    Map<String, Object> login(String email, String password);

    //下架商品
    int delPro(int p_id);

    //修改商品
    int updatePro(Map<String, Object> map);

    //添加商品图片
    int addPic(int p_id, String path);

    //删除图片
    int deletePic(int p_id, String path);

    //所有商品
    List<Pro> getAllPro();

    //添加商品
    int addPro(Pro pro, int c_id);

    //查询商品
    List<Pro> findPro(Map<String, Object> map);

    //封禁用户
    int banUser(int u_id,boolean flag);

    //封禁用户
    //注销用户
    int delUser(int u_id);

    //封禁用户
    //注销用户
    //查询用户
    List<User> findUser(Map<String, Object> map);

    //商品分页查询
    Map<String, Object> getProPage(int page, int size);

    //获取历史订单
    List<Order> getAllOrder();
}
