package com.zhiyou100.controller;

import com.zhiyou100.entity.ProjectEntity;
import com.zhiyou100.exception.CrowdFundingException;
import com.zhiyou100.service.ProjectService;
import com.zhiyou100.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/*
*@ClassName:ProjectController
 @Description:TODO
 @Author:
 @Date:2018/9/21 9:31 
 @Version:v1.0
*/
@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;


    @RequestMapping("/uploadProject.do")
    @ResponseBody
    public String uploadProject(ProjectEntity projectEntity) throws CrowdFundingException {
       projectEntity.setPsType(0);
        int rows = projectService.uploadProject(projectEntity);
        if (rows == 1) {
            return ResponseUtil.responseSuccess(1, "项目提交成功，请关注邮箱或者短信，以及时了解项目的最新动态");
        } else {
            throw new CrowdFundingException(2, "项目提交失败，请重试");
        }

    }
}
