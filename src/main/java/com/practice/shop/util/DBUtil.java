package com.practice.shop.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/29 18:20
 * @Description:
 **/
@Component
public class DBUtil {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/shop?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai";
    static final String USER = "root";
    static final String PASSWORD = "zrq66689";


//    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost:3306/shop?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai";
//    static final String USER = "root";
//    static final String PASSWORD = "zrq66689";
    static Connection conn = null;
    static JdbcTemplate jdbcTemplate = null;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);//驱动
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static Connection getConn() {
        return conn;
    }

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
