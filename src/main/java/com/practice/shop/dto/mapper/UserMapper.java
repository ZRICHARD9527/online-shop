package com.practice.shop.dto.mapper;

import com.practice.shop.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/31 17:19
 * @Description:
 **/

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        if (rs.wasNull()) {
            System.out.println("rs is null");
            return null;
        }
        user.setU_id(rs.getInt("u_id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setSex(rs.getInt("sex"));
        user.setEmail(rs.getString("email"));
        user.setPicpath(rs.getString("picpath"));
        user.setRegTime(rs.getTimestamp("reg_time"));
        user.setActiveFlag(rs.getBoolean("active_flag"));
        return user;
    }
}
