package com.zhiyou100.dao;


import com.zhiyou100.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

/*
*@ClassName:UserDao
 @Description:TODO
 @Author:
 @Date:2018/9/17 15:39 
 @Version:v1.0
*/
public interface UserDao {
    UserEntity findByPhoneAndPassword(@Param("name") String name, @Param("password") String password);
    UserEntity findByEmailAndPassword(@Param("name") String name, @Param("password") String password);
    int save(UserEntity user);
    int update(UserEntity user);

    String findEmailByPhone(String phone);

    int updateStatusByPhone(String phone);

    void updateMoneyById(@Param("id") int id, @Param("money")long money);

}
