package com.practice.shop.repository;

import com.practice.shop.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/8/10 9:39
 * @Description:
 **/

public interface UserRepository extends JpaRepository<User,Integer> {

}
