package com.practice.shop.dao;

import com.practice.shop.dto.Pro;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface IProDao {
    //获取所有商品
    List<Pro> getAllPro();

    //获取指定id商品
    Pro getById(int id);

    //获取热销商品
    List<Pro> getHot();

    //获取所有类别
    List<String> getCategory();

    //获取指定类别下所有商品
    List<Pro> getCatePro(String name);

    //搜索商品
    List<Pro> getSearch(String content);
}
