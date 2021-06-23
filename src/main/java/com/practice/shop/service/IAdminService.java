package com.practice.shop.service;

import com.practice.shop.dto.Pro;
import com.practice.shop.dto.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IAdminService {
    ResponseDTO login(String email, String password);

    ResponseDTO deletePro(int p_id);

    ResponseDTO updatePro(Map<String, Object> proMap);

    ResponseDTO addPic(int p_id, String path);

    ResponseDTO addPic(MultipartFile file, int p_id, String filePath);

    ResponseDTO deletePic(int p_id, String path);

    ResponseDTO addPro(Pro pro, int c_id);

    ResponseDTO proList();

    //搜索商品
    ResponseDTO findPro(Map<String, Object> map);

    //封禁用户
    ResponseDTO banUser(int u_id,boolean flag);

    //注销用户
    ResponseDTO delUser(int u_id);

    //查询用户
    ResponseDTO findUser(Map<String, Object> map);
//获取分页
    ResponseDTO getProPage(int page, int size);

    ResponseDTO getAllOrder();
}
