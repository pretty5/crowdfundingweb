package com.zhiyou100.task;

import com.zhiyou100.entity.RealCheckEntity;
import com.zhiyou100.exception.CrowdFundingException;
import com.zhiyou100.service.FaceCompareService;
import com.zhiyou100.service.RealCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/*
*@ClassName:RealCheckTask
 @Description:TODO
 @Author:
 @Date:2018/9/20 17:20 
 @Version:v1.0
*/
@Component
@Slf4j
public class RealCheckTask {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
    }

    @Autowired
    RealCheckService realCheckService;

    @Autowired
    FaceCompareService faceCompareService;



    public void realCheckByAI()  {
        //从数据库获取所有未审核的实名认证请求
        int page=1;
        while (true){
            List<RealCheckEntity> realCheckEntities = realCheckService.unRealCheckedList(page);
            log.info("从数据库中取出{}条记录",realCheckEntities.size());

            if (realCheckEntities.size()==0){
                break;
            }
            //遍历  调用AI借口  判断
            Iterator<RealCheckEntity> iterator = realCheckEntities.iterator();
            while (iterator.hasNext()){
                RealCheckEntity realCheckEntity = iterator.next();
                String idCardPositive = realCheckEntity.getIdCardPositive();
                String idCardHand = realCheckEntity.getIdCardHand();
                try {
                    //将判断结果 保存至数据库
                    boolean flag = faceCompareService.compare(idCardPositive, idCardHand);
                    if (flag){
                        log.info("认证成功，手机号{}",realCheckEntity.getPhone());
                        realCheckService.realCheck("success",realCheckEntity.getId());
                    }else{
                        log.info("认证失败，手机号{}",realCheckEntity.getPhone());
                        realCheckService.realCheck("failure",realCheckEntity.getId());
                    }
                } catch (CrowdFundingException e) {
                    log.error("error:",e);
                    continue;
                }

            }
            page++;
        }





    }
}
