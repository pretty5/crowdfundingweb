package com.zhiyou100.task;

import com.zhiyou100.entity.ProjectEntity;
import com.zhiyou100.entity.UserEntity;
import com.zhiyou100.exception.CrowdFundingException;
import com.zhiyou100.service.FundingService;
import com.zhiyou100.service.MailService;
import com.zhiyou100.service.ProjectService;
import com.zhiyou100.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/*
*@ClassName:CrowdFundingTask
 @Description:TODO
 @Author:
 @Date:2018/9/21 10:57 
 @Version:v1.0
*/

/*定时审核提交的项目*/
@Slf4j
@Component
public class CrowdFundingTask {


    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
    }

    @Autowired
    ProjectService projectService;

    @Autowired
    FundingService fundingService;

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    //处理所有的项目
    /*
    注意 事务方法 只有在方法运行完毕后 才会提交，因为projectService.findExpireProject(page)
    一直返回list，因此dealFailureProjects方法永远不会跳出 ，事务永远不会提交，因此数据库不会被修改，
    不过在实际中，不会出现此问题（项目再多，总有遍历完的时候，方法总有运行结束的时候）
     */
    @Transactional
    public void dealFailureProjects() {
        //遍历数据库中今天到期的众筹项目
        while (true) {
            int page = 1;
            List<ProjectEntity> projectEntityList = projectService.findExpireProject(page);
            if (projectEntityList.size() == 0) {
                break;
            }
            Iterator<ProjectEntity> iterator = projectEntityList.iterator();
            while (iterator.hasNext()) {
                //判断筹款金额是否 小于预期  小于则失败
                ProjectEntity projectEntity = iterator.next();
                dealOneFailureProject(projectEntity);
            }

            page++;
        }


    }

    @Transactional//处理一个项目
    public void dealOneFailureProject(ProjectEntity projectEntity){

        if (projectEntity.getPsGetmoney() < projectEntity.getPsMoney()) {
            //标记项目众筹失败
            projectService.markFailure(4, projectEntity.getPsId());

            //失败告知  参与方  发起方
            List<UserEntity> userEntityList = fundingService.findProjectParticipator(projectEntity.getPsId());
            for (UserEntity user :
                    userEntityList) {
                try {
                    mailService.sendMail(user.getEMail(), "您参与的" + projectEntity.getPsName() + "失败");
                    long money = fundingService.getMoneyByUserAndProject(user.getId(), projectEntity.getPsId());
                    //去user表中增加  投资金额到账户余额
                    userService.updateMoney(user.getId(), money);
                    mailService.sendMail(user.getEMail(), "钱已退回，请注意查收");
                    //System.out.println(1/0);
                } catch (CrowdFundingException e) {
                    log.error("error:", e);
                    continue;
                }
            }

            //告知发起方
            String email = userService.findEmailByPhone(projectEntity.getPsCustPhone());
            try {
                mailService.sendMail(email, "您发起的项目，筹款失败。");
            } catch (CrowdFundingException e) {
                log.error("error:", e);
            }


        }
    }


}

