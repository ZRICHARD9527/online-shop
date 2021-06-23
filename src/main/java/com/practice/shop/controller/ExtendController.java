package com.practice.shop.controller;

import com.practice.shop.dto.Pro;
import com.practice.shop.dto.ResponseDTO;
import com.practice.shop.dto.User;
import com.practice.shop.dto.mapper.ProMapper;
import com.practice.shop.repository.ProRepository;
import com.practice.shop.repository.UserRepository;
import com.practice.shop.util.DBUtil;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/8/8 15:31
 * @Description:
 **/
@Controller
public class ExtendController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProRepository proRepository;


    @GetMapping("/index")
    public String conTest() {
        return "index";
    }


    //获取分页的用户列表
    @ResponseBody
    @PostMapping("/userPage")
    public Page<User> getUsers(@RequestBody JSONObject jsonObject) {

        Integer page = (Integer) jsonObject.get("page") - 1;//当前页面
        Integer size = (Integer) jsonObject.get("size"); //每页大小
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);

    }

    //商品列表
    @ResponseBody
    @PostMapping("/proPage")
    public Page<Pro> getPros(@RequestBody JSONObject jsonObject) {
        Integer page = (Integer) jsonObject.get("page") - 1;//当前页面
        Integer size = (Integer) jsonObject.get("size"); //每页大小
        Pageable pageable = PageRequest.of(page, size);
        Page<Pro> pros = proRepository.findAll(pageable);

        System.out.println("pageable : " + pageable);
        System.out.println("pros : " + pros);

        System.out.println(pros.get());

        return pros;
    }

//    //商品列表
//    @ResponseBody
//    @PostMapping("/proPage")
//    public ResponseDTO getPros(@RequestBody JSONObject jsonObject) {
//        Integer page = (Integer) jsonObject.get("page") - 1;//当前页面
//        Integer size = (Integer) jsonObject.get("size"); //每页大小
//        int begin = page * size;
//        JdbcTemplate jdbcTemplate = DBUtil.getJdbcTemplate();
//        List<Pro> pros = jdbcTemplate.query("select * from pro limit ?,?", new ProMapper(), begin, size);
//        return new ResponseDTO(true, "", pros);
//    }


    //获取用户
    @GetMapping("/findById/{id}")
    public User findById(@PathVariable("id") Integer id) {
        return userRepository.findById(id).get();
    }

    @PostMapping("/update")
    public String update(@RequestBody User user) {
        User result = userRepository.save(user);//主键存在时为修改，否则为保存，返回新的值
        return (null == result) ? "false" : "success";
    }



}
