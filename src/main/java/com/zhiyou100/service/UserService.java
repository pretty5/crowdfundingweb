package com.zhiyou100.service;

import com.zhiyou100.dao.UserDao;
import com.zhiyou100.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
*@ClassName:UserService
 @Description:TODO
 @Author:
 @Date:2018/9/18 9:54 
 @Version:v1.0
*/
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public String findEmailByPhone(String phone) {
        return userDao.findEmailByPhone(phone);
    }

    public int realCheckPass(String phone) {

        return userDao.updateStatusByPhone(phone);

    }

    public void updateMoney(int id, long money) {
        userDao.updateMoneyById(id,money);
    }
}
