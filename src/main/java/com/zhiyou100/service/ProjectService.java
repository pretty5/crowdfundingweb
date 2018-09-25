package com.zhiyou100.service;

import com.github.pagehelper.PageHelper;
import com.zhiyou100.dao.ProjectDao;
import com.zhiyou100.entity.ProjectEntity;
import com.zhiyou100.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
*@ClassName:ProjectService
 @Description:TODO
 @Author:
 @Date:2018/9/21 9:53 
 @Version:v1.0
*/
@Service
public class ProjectService {
    @Autowired
    ProjectDao projectDao;

    public int uploadProject(ProjectEntity projectEntity){
        return projectDao.insert(projectEntity);
    }


    public List<ProjectEntity> findExpireProject(int page) {
        String date = DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        PageHelper.startPage(page,5);
        return projectDao.findByDate(date);
    }

    public void markFailure(int i, Integer psId) {
        //todo 标记项目状态 为失败
    }
}
