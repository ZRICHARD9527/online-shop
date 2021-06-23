package com.practice.shop.dto.mapper;

import com.practice.shop.dto.Pro;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/31 14:57
 * @Description:
 **/

public class ProMapper implements RowMapper<Pro> {

    @Override
    public Pro mapRow(ResultSet rs, int i) throws SQLException {
        Pro pro = new Pro();
        if (rs.wasNull()) {
            return pro;
        }
        pro.setP_id(rs.getInt("p_id"));
        pro.setName(rs.getString("name"));
        pro.setSn(rs.getString("sn"));
        pro.setNum(rs.getInt("num"));
        pro.setSales(rs.getInt("sales"));
        pro.setPrice(rs.getDouble("price"));
        pro.setDesc(rs.getString("desc"));
        pro.setTime(rs.getTimestamp("time"));
        return pro;
    }

}
