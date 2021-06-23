package com.practice.shop.service.impl;

import com.practice.shop.dao.IAdminDao;
import com.practice.shop.dao.impl.IAdminDaoImpl;
import com.practice.shop.dto.Pro;
import com.practice.shop.dto.ResponseDTO;
import com.practice.shop.dto.User;
import com.practice.shop.service.IAdminService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/8/6 15:35
 * @Description:
 **/

public class IAdminServiceImpl implements IAdminService {


    private IAdminDao iAdminDao = new IAdminDaoImpl();

    //管理员登录
    @Override
    public ResponseDTO login(String email, String password) {
        Map<String, Object> map = iAdminDao.login(email, password);
        if (null == map) {
            return new ResponseDTO(false, "账号或密码错误", 0);
        } else {
            return new ResponseDTO(true, "登录成功", map);
        }
    }

    /**
     * 删除商品
     *
     * @param p_id
     * @return
     */
    @Override
    public ResponseDTO deletePro(int p_id) {
        int i = iAdminDao.delPro(p_id);
        if (i >= 2) {
            return new ResponseDTO(true, "删除成功", null);
        }
        return new ResponseDTO(false, "删除失败", null);
    }

    /**
     * 修改商品
     *
     * @param map
     * @return
     */
    @Override
    public ResponseDTO updatePro(Map<String, Object> map) {
        int i = iAdminDao.updatePro(map);
        if (i == 1) {
            return new ResponseDTO(true, "修改成功", null);
        } else {
            return new ResponseDTO(false, "修改失败", null);
        }
    }

    /**
     * 添加商品图片
     *
     * @param file
     * @return
     */
    @Override
    public ResponseDTO addPic(MultipartFile file, int p_id, String filePath) {

        //获取文件名
        String fileName = file.getOriginalFilename();
        //上传文件名改为文件名加时间,用以唯一标记
        String newName = System.currentTimeMillis() + fileName;

        //新建文件，来判断目录是否存在
        File file1 = new File(filePath, fileName);
        if (!file1.getParentFile().exists()) {
            //不存在目录就新建
            file1.getParentFile().mkdirs();
        }
        //写入文件
        try {
            file.transferTo(new File(filePath + File.separator + newName));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseDTO(false, "添加失败", null);
        }

        //设置访问路径
        String path = "/img/" + newName;

        int i = iAdminDao.addPic(p_id, path);
        if (i == 1) {
            return new ResponseDTO(true, "添加成功", null);
        } else {
            return new ResponseDTO(false, "添加失败", null);
        }
    }

    @Override
    public ResponseDTO addPic(int p_id, String path) {
        int i = iAdminDao.addPic(p_id, path);
        if (i == 1) {
            return new ResponseDTO(true, "添加成功", null);
        } else {
            return new ResponseDTO(false, "添加失败", null);
        }
    }

    /**
     * 删除图片
     *
     * @param p_id
     * @param path
     * @return
     */
    @Override
    public ResponseDTO deletePic(int p_id, String path) {
        int i = iAdminDao.deletePic(p_id, path);
        if (i == 0) {
            return new ResponseDTO(false, "修改失败", null);
        } else {
            return new ResponseDTO(true, "修改成功", null);
        }
    }

    /**
     * 添加商品
     *
     * @param pro
     * @param c_id
     * @return
     */
    @Override
    public ResponseDTO addPro(Pro pro, int c_id) {
        int i = iAdminDao.addPro(pro, c_id);
        if (i == 2) {
            return new ResponseDTO(true, "添加成功", null);
        } else {
            return new ResponseDTO(false, "添加失败", null);
        }
    }

    /**
     * 商品列表
     *
     * @return
     */
    @Override
    public ResponseDTO proList() {
        Object data = iAdminDao.getAllPro();
        if (null != data) {
            return new ResponseDTO(true, "", data);
        } else {
            return new ResponseDTO(false, "暂无商品", null);
        }
    }

    //搜索商品
    @Override
    public ResponseDTO findPro(Map<String, Object> map) {
        Object data = iAdminDao.findPro(map);
        return new ResponseDTO(true, "", data);
    }

    //封禁用户
    @Override
    public ResponseDTO banUser(int u_id, boolean flag) {
        int i = iAdminDao.banUser(u_id, flag);
        if (i == 1) {
            return new ResponseDTO(true, "执行成功", null);
        } else {
            return new ResponseDTO(true, "执行失败", null);
        }
    }

    //注销用户
    @Override
    public ResponseDTO delUser(int u_id) {
        int i = iAdminDao.delUser(u_id);
        if (i == 1) {
            return new ResponseDTO(true, "执行成功", null);
        } else {
            return new ResponseDTO(true, "执行失败", null);
        }
    }

    //查询用户
    @Override
    public ResponseDTO findUser(Map<String, Object> map) {
//        System.out.println(map);
        List<User> users = iAdminDao.findUser(map);
        if (users.size() == 0) {
            return new ResponseDTO(false, "无用户", null);
        }
        return new ResponseDTO(true, "", users);
    }

    /**
     * 获取用户分页
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public ResponseDTO getProPage(int page, int size) {
        Map<String, Object> data = iAdminDao.getProPage(page, size);
//        System.out.println("data:\n"+data);
        if (data.isEmpty()) {
            return new ResponseDTO(false, "", data);
        }
        return new ResponseDTO(true, "", data);
    }

    /**
     * 获取所有订单
     *
     * @return
     */
    @Override
    public ResponseDTO getAllOrder() {
        return new ResponseDTO(true, "", iAdminDao.getAllOrder());
    }

}
