package com.practice.shop.dto.mapper;

import com.practice.shop.dto.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/31 21:24
 * @Description:
 **/

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        Order order = new Order();
        order.setO_id(rs.getInt("o_id"));
        order.setU_id(rs.getInt("u_id"));
        order.setP_id(rs.getInt("p_id"));
        order.setNum(rs.getInt("num"));
        order.setIsbuy(rs.getBoolean("isbuy"));
        order.setAddress(rs.getString("address"));
        order.setTime(rs.getTimestamp("time"));
        return order;
    }
}
