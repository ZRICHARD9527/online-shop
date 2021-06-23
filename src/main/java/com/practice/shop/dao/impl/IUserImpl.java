package com.practice.shop.dao.impl;

import com.practice.shop.dao.IProDao;
import com.practice.shop.dao.IUserDao;
import com.practice.shop.dto.Order;
import com.practice.shop.dto.User;
import com.practice.shop.dto.mapper.OrderMapper;
import com.practice.shop.dto.mapper.UserMapper;
import com.practice.shop.util.DBUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/31 21:06
 * @Description:
 **/

public class IUserImpl implements IUserDao {

    //    @Autowired
//    private JdbcTemplate jdbcTemplate;
    private JdbcTemplate jdbcTemplate = DBUtil.getJdbcTemplate();
    private IProDao iProDao = new IProDaoImpl();

    //注册
    @Override
    public int register(User user) {
        //首先判断用户是否存在(根据email)
        String sql = "select count(*) from user where email=?";
        int i = jdbcTemplate.queryForObject(sql, int.class, user.getEmail());
        if (i != 0) {
            return -1;
        }

        if (user.getPicpath() != null && !"".equals(user.getPicpath())) {
            sql = "insert into user(name,password,sex,email,picpath,reg_time,active_flag) value(?,?,?,?,?,?,?)";
            i = jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getSex(), user.getEmail(), user.getPicpath(), new Timestamp(System.currentTimeMillis()), true);
        } else {
            sql = "insert into user(name,password,sex,email,picpath,reg_time,active_flag) value(?,?,?,?,?,?,?)";
            i = jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getSex(), user.getEmail(), "/img/initImg.jpg", new Timestamp(System.currentTimeMillis()), true);
        }
        return i;
    }

    //通过邮箱和密码登录
    @Override
    public Map<String, Object> login(User user) {
        String sql = "select u_id,name,active_flag from user where email = ?  and  password = ?";
        Map<String, Object> map = null;
        try {
            map = jdbcTemplate.queryForMap(sql, user.getEmail(), user.getPassword());
        } catch (DataAccessException e) {
            //没有数据
            return null;
        } finally {
            return map;
        }
    }

    //通过id获取用户
    @Override
    public User getById(int id) {
        String sql = "select * from user where u_id=?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
        } catch (DataAccessException e) {
            return null;
        } finally {
            return user;
        }
    }


    //添加购物车
    @Override
    public int addToCart(int u_id, int p_id, int num) {
        String sql = "insert into `order`(u_id,p_id,num,isbuy,address,time) value(?,?,?,?,?,?)";
        int i = jdbcTemplate.update(sql, u_id, p_id, num, false, "", new Timestamp(System.currentTimeMillis()));
        return i;
    }

    //购买
    @Override
    public int purchase(int u_id, int p_id, int num, String address) {

        //判断库存
        String sql0 = "select num from pro where p_id=?";
        int pnum = jdbcTemplate.queryForObject(sql0, int.class, p_id);
        if (pnum < num) {
            //超出库存购买失败
            return -1;
        }

        //填入订单表
        if(null==address||"".equals(address)){
            address="长沙理工大学";
        }
        String sql = "insert into `order`(u_id,p_id,num,isbuy,address,time) value(?,?,?,?,?,?)";//由于order为mysql关键字，引用时需要用斜点标注
        int i = jdbcTemplate.update(sql, u_id, p_id, num, true, address, new Timestamp(System.currentTimeMillis()));
        if (i != 1) {
            //不为1则插入失败，直接返回
            return i;
        }
        //删除购物车同一商品
        String sql1 = "delete from `order` where u_id=? and p_id=? and isbuy=false";
        jdbcTemplate.update(sql1, u_id, p_id);

        //库存以及销量发生变化
        String sql2 = "update pro set num=(num-?) ,sales=sales+1 where p_id=?";
        jdbcTemplate.update(sql2, num, p_id);

        return i;
    }

    //获取购物车
    public List<Order> getCart(int u_id) {
        String sql = "select * from `order` where u_id=? and isbuy=false order by time desc";
        List<Order> orders = null;
        try {
            orders = jdbcTemplate.query(sql, new OrderMapper(), u_id);
            //获取订单对应商品
            for (Order o : orders
            ) {
                o.setPro(iProDao.getById(o.getP_id()));
            }
        } catch (DataAccessException e) {
            return null;
        } finally {
            return orders;
        }

    }


    //获取历史订单
    public List<Order> getOrder(int u_id) {
        String sql = "select * from `order` where u_id=? and isbuy=true";
        List<Order> orders = null;
        try {
            //获取历史订单
            orders = jdbcTemplate.query(sql, new OrderMapper(), u_id);

        } catch (DataAccessException e) {
            return null;
        } finally {
            //获取订单对应商品
            for (Order o : orders
            ) {
                o.setPro(iProDao.getById(o.getP_id()));
            }
            return orders;
        }

    }
}
