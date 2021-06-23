package com.practice.shop.service;

import com.practice.shop.dto.ResponseDTO;


public interface IProService {

    ResponseDTO home();

    ResponseDTO getProInfo(int p_id);

    ResponseDTO getCatePro(String name);

    ResponseDTO search(String content);
    //获取分类下商品
//    public List<Pro> getCatePro(int c_id);
    //获取搜索商品
//    public List<Pro> getSearch(String name);
}
