package com.zhiyou100.entity;

import lombok.Data;

import java.math.BigDecimal;

/*
*@ClassName:UserEntity
 @Description:TODO
 @Author:
 @Date:2018/9/17 15:19 
 @Version:v1.0
*/
@Data
public class UserEntity {
    private int id;
    private String idCard;
    private String name;
    private String password;
    private String code;
    private BigDecimal money;
    private String phone;
    private String eMail;
    private String address;
    private String sex;

}
