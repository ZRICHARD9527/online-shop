package com.practice.shop.controller;

import com.practice.shop.dto.Pro;
import com.practice.shop.dto.ResponseDTO;
import com.practice.shop.service.IAdminService;
import com.practice.shop.service.IUserService;
import com.practice.shop.service.impl.IAdminServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/8/3 20:33
 * @Description:
 **/

/**
 * 1.管理商品
 * 2.管理用户
 * 3.登录
 */
@Controller
@ResponseBody
public class AdminController {
    @Value("${file.upload.path}")
    private String filePath;
    private IAdminService iAdminService = new IAdminServiceImpl();

    /**
     * 管理员登录
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/admin")
    public ResponseDTO adminLogin(@RequestBody JSONObject jsonObject) {
        String email = jsonObject.getAsString("email");
        String password = jsonObject.getAsString("password");
        System.out.println(jsonObject);
        return iAdminService.login(email, password);
    }


    /**
     * 修改商品
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/updatePro")
    public ResponseDTO updatePro(@RequestBody JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        map.put("p_id", Integer.parseInt(jsonObject.getAsString("p_id")));
        map.put("name", jsonObject.getAsString("name"));
        map.put("sn", jsonObject.getAsString("sn"));
        map.put("num", jsonObject.getAsString("num"));
        map.put("price", jsonObject.getAsString("price"));
        map.put("desc", jsonObject.getAsString("desc"));
        map.put("c_id", Integer.parseInt(jsonObject.getAsString("c_id")));
        map.put("c_name", jsonObject.getAsString("c_name"));
        return iAdminService.updatePro(map);
    }

    /**
     * 添加商品
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("addPro")
    public ResponseDTO addPro(@RequestBody JSONObject jsonObject) {
        Pro pro = new Pro();
        pro.setName(jsonObject.getAsString("name"));
        pro.setSn(jsonObject.getAsString("sn"));
        pro.setNum(Integer.parseInt(jsonObject.getAsString("num")));
        pro.setPrice(Double.parseDouble(jsonObject.getAsString("price")));
        pro.setDesc(jsonObject.getAsString("desc"));

        int c_id = Integer.parseInt(jsonObject.getAsString("c_id"));

        System.out.println(pro);
        System.out.println(c_id);

        return iAdminService.addPro(pro, c_id);
    }

    /**
     * 下架商品
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/delPro")
    public ResponseDTO deletePro(@RequestBody JSONObject jsonObject) {
        int id = (int) jsonObject.get("p_id");
        return iAdminService.deletePro(id);
    }


    /**
     * 添加图片
     *
     * @param p_id
     * @param file
     * @return
     */
    @PostMapping("/addPic")
    public ResponseDTO addPicFile(@RequestParam("p_id") int p_id, @RequestParam("img") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseDTO(false, "emptyFile", null);
        }
        return iAdminService.addPic(file, p_id, filePath);
    }


    /**
     * 删除图片
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/delPic")
    public ResponseDTO delPic(@RequestBody JSONObject jsonObject) {
        int p_id = (int) jsonObject.get("p_id");
        String path = jsonObject.getAsString("path");
        return iAdminService.deletePic(p_id, path);
    }

    /**
     * 所有商品
     *
     * @return
     */
    @PostMapping("/proList")
    public ResponseDTO proList() {
        return iAdminService.proList();
    }

    /**
     * 查询商品
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/findPro")
    public ResponseDTO findPro(@RequestBody JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        map.put("p_id", jsonObject.get("p_id"));
        map.put("name", jsonObject.getAsString("name"));
        map.put("cate", jsonObject.getAsString("cate"));
        ResponseDTO responseDTO = iAdminService.findPro(map);
        System.out.println(map);
        System.out.println(responseDTO.toString());
        System.out.println(responseDTO.getData());
        return responseDTO;
    }

    /**
     * 通过id查询商品
     *
     * @param id
     * @return
     */
    @GetMapping("/getPro/{p_id}")
    public ResponseDTO getPro(@PathVariable("p_id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("p_id", id);
        map.put("content", null);
        System.out.println(iAdminService.findPro(map).getData());
        return iAdminService.findPro(map);
    }


    /**
     * 查询用户
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/findUser")
    public ResponseDTO findUser(@RequestBody JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        map.put("u_id", jsonObject.getAsString("u_id"));
        map.put("name", jsonObject.getAsString("name"));
        map.put("sex", jsonObject.getAsString("sex"));
//        System.out.println(map);
        return iAdminService.findUser(map);
    }

    /**
     * 封禁或激活用户
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/userActive")
    public ResponseDTO banUser(@RequestBody JSONObject jsonObject) {

        int u_id = (int) jsonObject.get("u_id");
        boolean flag = (Boolean) jsonObject.get("activeFlag");

        System.out.println(jsonObject);

        return iAdminService.banUser(u_id, flag);
    }

    /**
     * 注销用户
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/delUser")
    public ResponseDTO delUser(@RequestBody JSONObject jsonObject) {
        int u_id = Integer.parseInt(jsonObject.getAsString("u_id"));
        return iAdminService.delUser(u_id);
    }


    /**
     * 获取商品分页
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/getProPage")
    public ResponseDTO getProPage(@RequestBody JSONObject jsonObject) {
        Integer page = Integer.parseInt(jsonObject.getAsString("page")) - 1;
        Integer size = Integer.parseInt(jsonObject.getAsString("size"));
        return iAdminService.getProPage(page, size);
    }

    /**
     * 获取所有用户订单
     *
     * @return
     */
    @GetMapping("/allOrder")
    public ResponseDTO order() {
//        System.out.println(iAdminService.getAllOrder().getData());
        return iAdminService.getAllOrder();
    }

}
