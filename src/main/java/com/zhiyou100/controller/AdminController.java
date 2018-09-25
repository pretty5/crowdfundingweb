package com.zhiyou100.controller;

import com.zhiyou100.annotation.Permission;
import com.zhiyou100.entity.RealCheckEntity;
import com.zhiyou100.entity.UserEntity;
import com.zhiyou100.exception.CrowdFundingException;
import com.zhiyou100.service.MailService;
import com.zhiyou100.service.RealCheckService;
import com.zhiyou100.service.UserService;
import com.zhiyou100.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
*@ClassName:AdminController
 @Description:处理后台的业务逻辑 如 项目审核  实名审核
 @Author:
 @Date:2018/9/19 9:14 
 @Version:v1.0
*/
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    RealCheckService realCheckService;
    @Autowired
    MailService mailService;


    @RequestMapping("/manualRealCheck.do")
    @Permission(role={"super"})
    public void manualRealCheck(String result,int realCheckId) throws CrowdFundingException {
        realCheckService.realCheck(result,realCheckId);
    }
    //从数据库中读取   已经提交 未 审核的  实名认证请求
    @RequestMapping("/unRealCheckedList.do")
    @ResponseBody
    public String unRealCheckedList(String page){
        int intPage = Integer.parseInt(page);
        int pageNum=0;
        if (intPage>0){
             pageNum=intPage;
         }
         List<RealCheckEntity> realCheckEntityList=realCheckService.unRealCheckedList(pageNum);
         return  ResponseUtil.responseSuccess(1,realCheckEntityList);
    }


}
