package com.practice.shop.controller;

import com.practice.shop.dto.ResponseDTO;
import com.practice.shop.dto.User;
import com.practice.shop.service.IUserService;
import com.practice.shop.service.impl.IUserServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/29 18:06
 * @Description:
 **/

@Controller
@ResponseBody

public class UserController {

    private IUserService iUserService = new IUserServiceImpl();
//    private IUserDao iUserDao = new IUserImpl();

    /**
     * 注册
     * @param jsonobject
     * @return
     */
    @PostMapping("/register")
    public ResponseDTO register(@RequestBody JSONObject jsonobject) {
        User user = new User();
        user.setName(jsonobject.getAsString("name"));
        user.setPassword(jsonobject.getAsString("password"));
        user.setEmail(jsonobject.getAsString("email"));
        user.setPicpath(jsonobject.getAsString("picpath"));
        user.setSex(Integer.parseInt(jsonobject.getAsString("sex")));

        return iUserService.register(user);
    }

    /**
     * 用户登录，需要邮箱和密码,返回用户名和id
     * @param jsonObject
     * @return
     */
    @PostMapping("/login")
    public ResponseDTO login(@RequestBody JSONObject jsonObject) {
        User user = new User();
        user.setEmail(jsonObject.getAsString("email"));
        user.setPassword(jsonObject.getAsString("password"));
        return iUserService.login(user);
    }

    /**
     * 获取用户详情
     * @param jsonObject
     * @return
     */
    @PostMapping("/userInfo")
    public ResponseDTO userInfo(@RequestBody JSONObject jsonObject) {
        int id = (int) jsonObject.get("u_id");
        return iUserService.getUserInfo(id);
    }

    /**
     * 购买商品
     * @param jsonObject
     * @return
     */
    @PostMapping("/purchase")
    public ResponseDTO purchase(@RequestBody JSONObject jsonObject) {
        int u_id = (int) jsonObject.get("u_id");
        int p_id = (int) jsonObject.get("p_id");
        int num = (int) jsonObject.get("num");
        String address = jsonObject.getAsString("address");
        return iUserService.purchase(u_id, p_id, num, address);
    }

    /**
     * 添加购物车
     * @param jsonObject
     * @return
     */
    @PostMapping("/addCart")
    public ResponseDTO addCart(@RequestBody JSONObject jsonObject) {
        int u_id = (int) jsonObject.get("u_id");
        int p_id = (int) jsonObject.get("p_id");
        int num = (int) jsonObject.get("num");

        return iUserService.addToCart(u_id, p_id, num);

    }

    /**
     * 获取用户购物车
     * @param jsonObject
     * @return
     */
    @PostMapping("/cart")
    public ResponseDTO cart(@RequestBody JSONObject jsonObject) {
        int id = Integer.parseInt( jsonObject.getAsString("u_id"));
        return iUserService.getCart(id);
    }

    /**
     * 用户历史订单
     * @param jsonObject
     * @return
     */
    @PostMapping("/order")
    public ResponseDTO order(@RequestBody JSONObject jsonObject) {
        int id = (int) jsonObject.get("u_id");
        return iUserService.getOrder(id);
    }
}
