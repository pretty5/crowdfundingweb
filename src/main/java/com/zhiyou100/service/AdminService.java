package com.zhiyou100.service;

import com.zhiyou100.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
*@ClassName:AdminService
 @Description:TODO
 @Author:
 @Date:2018/9/19 9:50 
 @Version:v1.0
*/
@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;

    public List<String> findRolesById(int id){
        return adminDao.findRolesById(id);
    }



}
