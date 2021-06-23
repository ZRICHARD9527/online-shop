package com.practice.shop.service.impl;

import com.practice.shop.dao.IUserDao;
import com.practice.shop.dao.impl.IUserImpl;
import com.practice.shop.dto.Order;
import com.practice.shop.dto.ResponseDTO;
import com.practice.shop.dto.User;
import com.practice.shop.service.IUserService;

import java.util.List;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/30 20:32
 * @Description:
 **/

public class IUserServiceImpl implements IUserService {

    //    @Autowired
//    User userDTO;
    private IUserDao iUserDao = new IUserImpl();

    @Override
    public ResponseDTO login(User _user) {
        Map<String, Object> map = iUserDao.login(_user);
        if (null == map) {
            return new ResponseDTO(false, "账号或密码错误", 0);
        } else if (!(boolean) map.get("active_flag")) {
            return new ResponseDTO(false, "该用户已暂停服务", -1);
        } else {
            map.remove("active_flag");
            return new ResponseDTO(true, "登录成功", map);
        }
    }

    @Override
    public ResponseDTO register(User user) {
        int i = iUserDao.register(user);
        if (i == -1) {
            return new ResponseDTO(false, "该邮箱用户已存在", -1);
        } else if (i == 0) {
            return new ResponseDTO(false, "注册失败", 0);
        } else {
            return new ResponseDTO(true, "注册成功", i);
        }
    }

    @Override
    public ResponseDTO getUserInfo(int u_id) {
        User user = iUserDao.getById(u_id);
        if (null == user) {
            return new ResponseDTO(false, "查看失败", null);
        } else {
//            user.setPassword(null);
            return new ResponseDTO(true, "", user);
        }

    }

    @Override
    public ResponseDTO purchase(int u_id, int p_id, int num, String address) {
        int i = iUserDao.purchase(u_id, p_id, num, address);
        if (i == -1) {
            return new ResponseDTO(false, "超出库存", -1);
        } else if (i == 0) {
            return new ResponseDTO(false, "购买失败", 0);
        } else {
            return new ResponseDTO(false, "购买成功", 1);
        }
    }

    @Override
    public ResponseDTO addToCart(int u_id, int p_id, int num) {
        int i = iUserDao.addToCart(u_id, p_id, num);
        if (i == 1) {
            return new ResponseDTO(true, "加入购物车", 1);
        } else {
            return new ResponseDTO(false, "添加失败", 0);
        }
    }

    @Override
    public ResponseDTO getCart(int u_id) {
        List<Order> orders = iUserDao.getCart(u_id);
        if (null == orders || orders.size() == 0) {
            return new ResponseDTO(false, "空空如也", null);
        } else {
            return new ResponseDTO(true, "", orders);
        }
    }

    @Override
    public ResponseDTO getOrder(int u_id) {
        List<Order> orders = iUserDao.getOrder(u_id);
        if (null == orders || orders.size() == 0) {
            return new ResponseDTO(false, "空空如也", null);
        } else {
            return new ResponseDTO(true, "", orders);
        }
    }

}
