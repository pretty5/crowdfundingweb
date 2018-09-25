package com.zhiyou100.dao;

import java.util.List;

/*
*@ClassName:AdminDao
 @Description:TODO
 @Author:
 @Date:2018/9/19 9:45 
 @Version:v1.0
*/
public interface AdminDao {
   List<String> findRolesById(int id);
}
