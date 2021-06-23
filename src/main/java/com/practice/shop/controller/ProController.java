package com.practice.shop.controller;

import com.practice.shop.dto.ResponseDTO;
import com.practice.shop.service.IProService;
import com.practice.shop.service.impl.IProServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/30 21:19
 * @Description:
 **/

@Controller
@ResponseBody//返回json或xml数据
public class ProController {

    private IProService iProService = new IProServiceImpl();


    //首页
    @GetMapping("/home")
    public ResponseDTO getIndex() {
        return iProService.home();
    }

    //获取商品详情
    @PostMapping("/pro")
    public ResponseDTO getById(@RequestBody JSONObject jsonObject) {
        return iProService.getProInfo((int) jsonObject.get("p_id"));
    }

    //获取分类下的所有商品
    @PostMapping("/onCate")
    public ResponseDTO getProUnderCate(@RequestBody JSONObject jsonObject) {
        return iProService.getCatePro(jsonObject.getAsString("name"));
    }

    //搜索商品
    @PostMapping("/search")
    public ResponseDTO getSearch(@RequestBody JSONObject jsonObject) {
        String str = jsonObject.getAsString("content");
        System.out.println(jsonObject);
        return iProService.search(str);
    }

    @RequestMapping("/thymeleaf/demo")
    public String hello(Model model, ModelMap modelMap) {
        model.addAttribute("hello", "thymeleaf");
        modelMap.addAttribute("hi", "thymeleaf");
        return "index";
    }

}
