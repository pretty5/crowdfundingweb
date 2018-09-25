package com.zhiyou100.service;

import com.github.pagehelper.PageHelper;
import com.zhiyou100.dao.RealCheckDao;
import com.zhiyou100.entity.RealCheckEntity;
import com.zhiyou100.exception.CrowdFundingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
*@ClassName:RealCheckService
 @Description:TODO
 @Author:
 @Date:2018/9/18 15:18 
 @Version:v1.0
*/
@Service
public class RealCheckService {

    @Autowired
    RealCheckDao realCheckDao;
    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    public int insert(RealCheckEntity realCheckEntity){
        return realCheckDao.insert(realCheckEntity);
    }


    public List<RealCheckEntity> unRealCheckedList(int pageNum) {
        PageHelper.startPage(pageNum,5);
        return realCheckDao.unRealCheckedList();

    }

    public void updateStatusById(int status, int realCheckId) {
        realCheckDao.updateStatusById(status,realCheckId);

    }

    public RealCheckEntity findById(int realCheckId) {
        return  realCheckDao.findById(realCheckId);
    }

    @Transactional
    public void realCheck(String result, int realCheckId) throws CrowdFundingException {
        int status=0;
        if (result.equals("success")){
            status=1;
            updateStatusById(status,realCheckId);

            //给用户 发信息 /邮件   告知其是实名认证成功
            RealCheckEntity realCheckEntity=findById(realCheckId);

            //修改用户的状态  已经认证通过
            userService.realCheckPass(realCheckEntity.getPhone());

            String email=userService.findEmailByPhone(realCheckEntity.getPhone());

            mailService.sendMail(email,"实名审核成功,您可以发布项目");

        }
        if (result.equals("failure")){
            status=2;
            updateStatusById(status,realCheckId);
            //给用户 发信息 /邮件   告知其是实名认证失败
            RealCheckEntity realCheckEntity=findById(realCheckId);
            String email=userService.findEmailByPhone(realCheckEntity.getPhone());
            mailService.sendMail(email,"实名审核失败,请重新提交资料");
        }

    }
}
