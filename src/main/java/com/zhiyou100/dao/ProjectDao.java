package com.zhiyou100.dao;

import com.zhiyou100.entity.ProjectEntity;

import java.util.List;

/*
*@ClassName:ProjectDao
 @Description:TODO
 @Author:
 @Date:2018/9/21 9:47 
 @Version:v1.0
*/
public interface ProjectDao {
     int insert(ProjectEntity projectEntity);

     List<ProjectEntity> findByDate(String date);
}
