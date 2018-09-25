package com.zhiyou100.controller;

import com.zhiyou100.exception.CrowdFundingException;
import com.zhiyou100.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
*@ClassName:ExceptionController
 @Description:TODO
 @Author:
 @Date:2018/9/18 17:28 
 @Version:v1.0
*/
@Slf4j
public class ExceptionController {
    //表示的是当该类 的方法 出现异常时  会调用该方法处理
    @ExceptionHandler
    @ResponseBody
    public String handleException(HttpServletRequest request, HttpServletResponse response, Exception ex){
        log.error("error:",ex);
        if (ex instanceof CrowdFundingException){
            CrowdFundingException crowdFundingException = (CrowdFundingException) ex;
            return  ResponseUtil.responseError(crowdFundingException.getCode(),crowdFundingException.getError());
        }else{
            return ResponseUtil.responseError(2,"请求失败");
        }
    }

}
