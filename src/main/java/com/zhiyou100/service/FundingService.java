package com.zhiyou100.service;

import com.zhiyou100.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
*@ClassName:FundingService
 @Description:TODO
 @Author:
 @Date:2018/9/21 11:09 
 @Version:v1.0
*/
@Service
public class FundingService {

    public long getMoneyByUserAndProject(int id, Integer psId) {
        //todo   数据库操作
        return 100;
    }

    public List<UserEntity> findProjectParticipator(Integer psId) {
        //todo  数据库查询
        //模拟
        ArrayList<UserEntity> list = new ArrayList<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setEMail("691734536@qq.com");
        userEntity.setId(1);
        userEntity.setPhone("12345678901");
        list.add(userEntity);
        return list;
    }
}
