package com.practice.shop.dao.impl;

import com.practice.shop.dao.IAdminDao;
import com.practice.shop.dao.IProDao;
import com.practice.shop.dto.Order;
import com.practice.shop.dto.Pro;
import com.practice.shop.dto.User;
import com.practice.shop.dto.mapper.OrderMapper;
import com.practice.shop.dto.mapper.ProMapper;
import com.practice.shop.dto.mapper.UserMapper;
import com.practice.shop.util.DBUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/8/6 15:38
 * @Description:
 **/

public class IAdminDaoImpl implements IAdminDao {

    private JdbcTemplate jdbcTemplate = DBUtil.getJdbcTemplate();
    private IProDao iProDao = new IProDaoImpl();

    //通过邮箱和密码登录
    @Override
    public Map<String, Object> login(String email, String password) {
        String sql = "select a_id,name from admin where email = ?  and  password = ?";
        Map<String, Object> map = null;
        try {
            map = jdbcTemplate.queryForMap(sql, email, password);
        } catch (DataAccessException e) {
            //没有数据
            return null;
        } finally {
            return map;
        }
    }

    //下架商品
    @Override
    public int delPro(int p_id) {
        //清除商品
        String sql = "delete from pro where p_id=?";
        int i = jdbcTemplate.update(sql, p_id);
        //清除分类
        String sql1 = "delete from cate where p_id=?";
        int i1 = jdbcTemplate.update(sql1, p_id);
        //清除图库
        String sql2 = "delete from album where p_id=?";
        int i2 = jdbcTemplate.update(sql2, p_id);
        return i + i1 + i2;
    }

    //更新商品
    @Override
    public int updatePro(Map<String, Object> map) {
        String sql0 = "select * from pro where p_id=?";
        Map<String, Object> Omap = jdbcTemplate.queryForMap(sql0, map.get("p_id"));
        for (String key : map.keySet()
        ) {
            if (null == map.get(key)) {
                //没有值,则使用原值替换
                map.put(key, Omap.get(key));
            }
        }

        //数据更新
        String sql = "update pro set `name`=?,`sn`=?,`num`=?,`price`=?,`desc`=? where p_id=?";
        int i = jdbcTemplate.update(sql, map.get("name"), map.get("sn"), map.get("num"), map.get("price"), map.get("desc"), map.get("p_id"));

        //类别更新

        //获取类别名称
        String c_name = jdbcTemplate.queryForObject("select c_name from cate where c_id=? limit 1", String.class, map.get("c_id"));
        //填入类别库
        jdbcTemplate.update("update `cate` set c_id =?,c_name=? where p_id=?", map.get("c_id"), c_name, map.get("p_id"));

        return i;
    }

    //添加商品图片
    @Override
    public int addPic(int p_id, String path) {
        String sql = "insert into album(p_id,picpath) value(?,?)";
        int i = jdbcTemplate.update(sql, p_id, path);
        return i;
    }

    //删除图片
    @Override
    public int deletePic(int p_id, String path) {
        String sql = "delete from album where p_id=? and picpath=?";
        int i = jdbcTemplate.update(sql, p_id, path);
        // 路径为文件且不为空则进行删除
        path = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + path.substring(5);
        System.out.println(path);
        File file = new File(path);
        System.out.println(file);
        System.out.println(file.getPath());
        if (file.isFile() && file.exists()) {
            file.delete();
        }

        return i;
    }

    //所有商品
    @Override
    public List<Pro> getAllPro() {
        IProDao iProDao = new IProDaoImpl();
        return iProDao.getAllPro();
    }

    //添加商品
    @Override
    public int addPro(Pro pro, int c_id) {

        System.out.println(c_id);
        System.out.println(pro);

        //插入商品数据
        String sql0 = "insert into `pro`(`name`,`sn`,`num`,`sales`,`price`,`desc`,`time`) value (?,?,?,0,?,?,?)";
        int i = jdbcTemplate.update(sql0, pro.getName(), pro.getSn(), pro.getNum(), pro.getPrice(), pro.getDesc(), new Timestamp(System.currentTimeMillis()));

        //获取分类名
        String sql1 = "select c_name from cate where c_id=? limit 1";
        String c_name = jdbcTemplate.queryForObject(sql1, String.class, c_id);

        //获取商品id
        String sql2 = "select p_id from pro where name=? and sn=? limit 1";
        int p_id = jdbcTemplate.queryForObject(sql2, int.class, pro.getName(), pro.getSn());

        //插入分类数据
        String sql3 = "insert into cate (p_id, c_id, c_name) value (?,?,?)";
        int i1 = jdbcTemplate.update(sql3, p_id, c_id, c_name);

        return i + i1;
    }

    /**
     * 查询商品
     *
     * @param map
     * @return
     */
    @Override
    public List<Pro> findPro(Map<String, Object> map) {

        List<Pro> pros = new ArrayList<>();

        //1.id查找,如果id可以找到直接返回
        if (map.get("p_id") != null && !"".equals(map.get("p_id").toString().trim())) {
            //id如果不为空或者空格
            Integer p_id;
            try {
                p_id = (Integer) map.get("p_id");
            } catch (Exception e) {
                //若传过来的不是数字则先转为字符串再转数字
                p_id = Integer.parseInt((String) map.get("p_id"));
            }

            String sql = "select count(*) from pro where p_id=?";
            int count = jdbcTemplate.queryForObject(sql, int.class, p_id);
            if (count == 1) {
                pros.add(iProDao.getById(p_id));
                return pros;
            }
        }
        //2.商品名，有则累加
        List<Integer> list1 = new ArrayList<>();
        if (map.get("name") != null&&!"".equals(map.get("name").toString().trim())) {
            String name = (String) map.get("name");
            String sql = "select p_id from pro where name RLIKE concat('[',?,']','+')";
            try {
                list1 = jdbcTemplate.queryForList(sql, Integer.class, name);
            } catch (DataAccessException e) {
                System.out.println("IAdminDaoImpl 168 无法匹配到该姓名" + name);
            }
        }
        //3.类别，有则去重、去空累加
        List<Integer> list2 = new ArrayList<>();
        if (map.get("cate") != null&&!"".equals(map.get("cate").toString().trim())) {
            String cate = (String) map.get("cate");
            String sql = "select p_id from `cate` where c_name=?";
            try {
                list2 = jdbcTemplate.queryForList(sql, Integer.class, cate);
            } catch (DataAccessException e) {
                System.out.println("IAdminDaoImpl 179 无法匹配到该类别" + cate);
            }
        }

        List<Integer> idList = new ArrayList<>();

        if (list1.size() != 0) {
            idList.addAll(list1);
        }

        if (idList.isEmpty()) {
            /**
             * 如果条件一（商品名）没有匹配到的话，lidList就只是条件二（类别）匹配的结果
             */
            idList.addAll(list2);
        } else if (list2.size() != 0) {
            /**
             * 条件一（商品名）存在
             * 条件二（类别）也存在，则为交集
             */
            idList.retainAll(list2);
        }

        for (int id : idList
        ) {
            pros.add(iProDao.getById(id));
        }

        return pros;
    }

    //封禁用户
    @Override
    public int banUser(int u_id, boolean flag) {
        String sql = "update user set active_flag=? where u_id=?";
        return jdbcTemplate.update(sql, flag, u_id);
    }

    /**
     * 注销用户
     *
     * @param u_id
     * @return
     */
    @Override
    public int delUser(int u_id) {
        String sql = "delete from user where u_id=?";
        return jdbcTemplate.update(sql, u_id);
    }

    /**
     * 查询用户,求交集
     *
     * @param map
     * @return
     */
    @Override
    public List<User> findUser(Map<String, Object> map) {
        //System.out.println(map);

        List<User> list = new ArrayList<>();
        //1.id
        if (null != map.get("u_id") && !"".equals(map.get("u_id").toString().trim())) {

            Integer u_id = Integer.parseInt((String) map.get("u_id"));
            String sql = "select count(*) from user where u_id=?";
            int count = jdbcTemplate.queryForObject(sql, int.class, u_id);

            //存在该用户则添加后返回
            if (count == 1) {
                list.add(getUserById(u_id));
//                System.out.println(u_id);
//                System.out.println(list);
            }
            return list;
        }
        //2.name
        List<Integer> ids1 = new ArrayList<>();
        if (null != map.get("name")&&!"".equals(map.get("name").toString().trim())) {
            String name = (String) map.get("name");
            System.out.println("name :"+name+"\tname.length():"+name.length());
            String sql = "select u_id from `user` where name RLIKE concat('[',?,']','+')";
            ids1 = jdbcTemplate.queryForList(sql, Integer.class, name);
        }
        //3.sex
        List<Integer> ids2 = new ArrayList<>();
        if (null != map.get("sex")&&!"".equals(map.get("sex").toString().trim())) {
            Integer sex = Integer.parseInt((String) map.get("sex"));
            String sql = "select u_id from `user` where sex=?";
            ids2 = jdbcTemplate.queryForList(sql, Integer.class, sex);
        }

        //求交集
        List<Integer> idList = new ArrayList<>();//存放交集
        idList.addAll(ids1);

        if (idList.size() == 0) {
            //如果没有匹配姓名的话直接加上匹配性别的集合
            idList.addAll(ids2);
        } else if (idList.size() != 0 && ids2.size() != 0) {
            //同时匹配姓名和性别则可以直接求交集（否则不行，因为有一个为null则交集为null）
            idList.retainAll(ids2);
        }

        for (int i : idList
        ) {
            list.add(getUserById(i));
        }
        return list;
    }


    /**
     * 通过id获取用户
     *
     * @param u_id
     * @return
     */
    public User getUserById(int u_id) {
        String sql = "select * from user where u_id=?";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), u_id);
    }

    /**
     * 商品分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map<String, Object> getProPage(int page, int size) {

        Map<String, Object> map = new HashMap<>();
        List<Pro> pros = new ArrayList<>();
        //获取当前分页数据
        //1.商品信息
        String sql = "select * from `pro` limit ?,?";
        pros = jdbcTemplate.query(sql, new ProMapper(), page * size, size);
        map.put("pros", pros);
        //2.商品图片
        String sql1 = "select picpath from album where p_id=?";
        for (Pro pro : pros) {
            int p_id = pro.getP_id();
            try {
                pro.setPicpath(jdbcTemplate.queryForList(sql1, String.class, p_id));
            } catch (DataAccessException e) {
                System.out.println("商品无图片" + pro);
                pro.setPicpath(null);
            }
            try {
                pro.setC_id(jdbcTemplate.queryForObject("select c_id from cate where p_id=? limit 1", Integer.class, p_id));
            } catch (DataAccessException e) {
                System.out.println("商品没有对应分类 IAdminDaoImpl.java:279\n" + pro);
                pro.setC_id(0);
                jdbcTemplate.update("insert into cate(p_id, c_id, c_name) value (?,0,'未分类')", pro.getP_id());
            }
        }
        //获取总元素数
        String sql2 = "select count(*) from `pro` ";
        Integer totalElements = jdbcTemplate.queryForObject(sql2, Integer.class);
        map.put("totalElements", totalElements);

        //获取所有种类
        List<String> cates = iProDao.getCategory();
        map.put("cates", cates);

        return map;
    }

    //获取历史订单
    @Override
    public List<Order> getAllOrder() {
        String sql = "select * from `order` where isbuy=true order by time desc";
        List<Order> orders = null;
        try {
            //获取历史订单
            orders = jdbcTemplate.query(sql, new OrderMapper());

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
