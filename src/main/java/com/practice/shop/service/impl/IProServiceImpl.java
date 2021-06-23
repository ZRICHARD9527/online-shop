package com.practice.shop.service.impl;

import com.practice.shop.dao.IProDao;
import com.practice.shop.dao.impl.IProDaoImpl;
import com.practice.shop.dto.Pro;
import com.practice.shop.dto.ResponseDTO;
import com.practice.shop.service.IProService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/7/30 20:05
 * @Description:
 **/

@Service("IUserService")
public class IProServiceImpl implements IProService {

//    @Autowired
//    private IProDao iProDao;
    private IProDao iProDao = new IProDaoImpl();

    @Override
    public ResponseDTO home() {
        //获取所有分类
        List<String> cate = iProDao.getCategory();

        //获取热门商品
        List<Pro> hotPro = iProDao.getHot();

        //获取所有商品
        List<Pro> allPro = iProDao.getAllPro();

        List<Object> list = new ArrayList<>();
        list.add(cate);
        list.add(hotPro);
        list.add(allPro);
        return new ResponseDTO(true, "", list);
    }

    @Override
    public ResponseDTO getProInfo(int p_id) {
        return new ResponseDTO(true, "", iProDao.getById(p_id));
    }

    @Override
    public ResponseDTO getCatePro(String name) {
        List<Pro> pros = iProDao.getCatePro(name);
        if (pros.size() == 0) {
            return new ResponseDTO(false, "该分类下暂无商品", null);
        } else {
            return new ResponseDTO(true, "", pros);
        }
    }

    @Override
    public ResponseDTO search(String content) {
        List<Pro> pros = iProDao.getSearch(content);
        if (pros.size() == 0) {
            return new ResponseDTO(false, "该商品暂未收录", null);
        } else {
            return new ResponseDTO(true, "", pros);
        }
    }

}
