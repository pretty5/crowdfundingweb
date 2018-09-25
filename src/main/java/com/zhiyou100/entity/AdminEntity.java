package com.zhiyou100.entity;

import lombok.Data;

import java.util.List;


/*
*@ClassName:AdminEntity
 @Description:TODO
 @Author:
 @Date:2018/9/19 9:41 
 @Version:v1.0
*/
@Data
public class AdminEntity {
    private int id;
    private String name;
    private List<String> roles;
}
