package com.practice.shop.service;


import com.practice.shop.dto.ResponseDTO;
import com.practice.shop.dto.User;

public interface IUserService {

    ResponseDTO login(User user);

    ResponseDTO register(User user);

    ResponseDTO getUserInfo(int id);

    ResponseDTO purchase(int u_id, int p_id, int num, String address);

    ResponseDTO addToCart(int u_id, int p_id, int num);

    ResponseDTO getCart(int u_id);

    ResponseDTO getOrder(int u_id);
}
