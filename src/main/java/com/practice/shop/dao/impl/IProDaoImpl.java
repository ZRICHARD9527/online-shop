package com.practice.shop.dao.impl;

import com.practice.shop.dao.IProDao;
import com.practice.shop.dto.Pro;
import com.practice.shop.dto.mapper.ProMapper;
import com.practice.shop.util.DBUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/29 22:17
 * @Description:
 **/

public class IProDaoImpl implements IProDao {

    private JdbcTemplate jdbcTemplate = DBUtil.getJdbcTemplate();

    /**
     * 获取所有商品
     *
     * @return
     */
    @Override
    public List<Pro> getAllPro() {

        String sql = "select * from pro";
        List<Pro> list = jdbcTemplate.query(sql, new ProMapper());
        for (Pro pro : list
        ) {
            pro.setPicpath(getPic(pro.getP_id()));
        }
        return list;
    }

    /**
     * 根据id返回商品详情
     *
     * @param id
     * @return
     */
    @Override
    public Pro getById(int id) {

        String sql = "select * from pro where p_id=?";
        Pro pro = null;
        try {
            pro = jdbcTemplate.queryForObject(sql, new ProMapper(), id);
            pro.setPicpath(getPic(id));//获取图片
            pro.setC_id(jdbcTemplate.queryForObject("select c_id from `cate` where p_id=? limit 1", Integer.class, id));
        } catch (DataAccessException e) {
            e.getStackTrace();
        } finally {
            return pro;
        }

    }

    /**
     * 通过商品id获得商品所有图片
     *
     * @param id 商品id
     * @return
     */
    public List<String> getPic(int id) {
        String sql = "select picpath from album where p_id=?";

        /**
         * 读取字段可以直接使用 类型.class
         */
        return jdbcTemplate.queryForList(sql, String.class, id);

        /**
         * 也可使用rowMapper
         */
//        RowMapper<String> rowMapper = (rs, rowNum) -> {
//            String pic = null;
//            if(rs.wasNull()){
//                return null;
//            }
//            pic=rs.getString("picpath");
//            return pic;
//        };
//        return jdbcTemplate.query(sql, new Object[]{id}, rowMapper);

    }


    /**
     * 通过销量选出销量前三的商品
     *
     * @return
     */
    @Override
    public List<Pro> getHot() {
        String sql = "select p_id from pro order by sales desc limit 3";
        List<Integer> id = jdbcTemplate.queryForList(sql, int.class);
        List<Pro> pros = new ArrayList<>();

        for (int i : id
        ) {
            pros.add(getById(i));
        }

        return pros;
    }

    /**
     * 获取所有商品类别
     *
     * @return
     */
    @Override
    public List<String> getCategory() {
        String sql = "select distinct(c_name) from cate";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    /**
     * 获取指定类别下所有商品
     *
     * @param name
     * @return
     */
    @Override
    public List<Pro> getCatePro(String name) {
        String sql = "select p_id from cate where c_name = ?";
        List<Integer> p_id = jdbcTemplate.queryForList(sql, int.class, name);
        List<Pro> list = new ArrayList<>();
        for (int i : p_id
        ) {
            list.add(getById(i));
        }
        return list;
    }

    /**
     * 获取搜索商品
     *
     * @param content 搜索内容
     * @return
     */
    @Override
    public List<Pro> getSearch(String content) {

        HashSet<Integer> hashSet = new HashSet<>();//存储满足条件的id
        List<Pro> pros = new ArrayList<>();
        List<Integer> pidList1 = null;
        //1.获得对应商品名的id
        String sql1 = "select p_id from pro where name REGEXP concat('[',?,']','+')";
        try {
            pidList1 = jdbcTemplate.queryForList(sql1, int.class, content);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        if (!pidList1.isEmpty()) {
            hashSet.addAll(pidList1);
        }

        //2.查询类别名下商品id
        String sql2 = "select p_id from cate where c_name REGEXP concat('[',?,']','+')";
        List<Integer> pidList2 = jdbcTemplate.queryForList(sql2, int.class, content);

        //3.根据商品id获得商品,清除元素、去掉查询重复值
        if (!pidList2.isEmpty()) {
            hashSet.addAll(pidList2);
        }
        pidList1.clear();
        pidList1.addAll(hashSet);

        Pro temp = null;
        for (int i : pidList1
        ) {
            temp = getById(i);
            if (temp != null) {
                pros.add(temp);
            } else {
                //清除数据库垃圾数据
                jdbcTemplate.update("delete from cate where p_id=?", i);
            }
        }
//        System.out.println(pros);
        return pros;
    }

    /**
     * 较原始的操作数据库方法
     */
//        Connection conn = DBUtil.getConn();
//        String sql = "select * from pro where p_id=?";
//        Pro pro = null;
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, id);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                pro = new Pro();
//                pro.setP_id(rs.getInt("p_id"));
//                pro.setName(rs.getString("name"));
//
//           }
}


