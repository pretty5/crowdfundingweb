package com.zhiyou100.controller;

import com.aliyuncs.exceptions.ClientException;
import com.zhiyou100.entity.RealCheckEntity;
import com.zhiyou100.entity.UserEntity;
import com.zhiyou100.exception.CrowdFundingException;
import com.zhiyou100.service.MailService;
import com.zhiyou100.service.OSSService;
import com.zhiyou100.service.RealCheckService;
import com.zhiyou100.service.ShortMessageService;
import com.zhiyou100.util.Response;
import com.zhiyou100.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/*
*@ClassName:UserController
 @Description:TODO
 @Author:
 @Date:2018/9/17 9:53 
 @Version:v1.0
*/
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController extends ExceptionController {
    @Autowired
    ShortMessageService shortMessageService;
    @Autowired
    MailService mailService;

    @Autowired
    OSSService ossService;
    @Autowired
    RealCheckService realCheckService;


    @ResponseBody
    @RequestMapping("/ex.do")
    public String ex() throws CrowdFundingException {
        throw new CrowdFundingException(2, "自定义异常");

    }


    @ResponseBody
    @RequestMapping("/verifyCode.do")
    public String getVerifyCode(String phone, HttpServletRequest request) throws ClientException {
        if (StringUtils.isEmpty(phone)) {
            return ResponseUtil.responseError(1, "手机号不能为空");
        }
        String code = generateVerifyCode(4);
        //发送信息

        shortMessageService.sendVerifyCode(phone, code);

        //保存验证码
        request.getSession().setAttribute("code", code);
        //设置验证码时长
        long current = System.currentTimeMillis();
        long expireTime = current + 1000 * 60 * 5;
        request.getSession().setAttribute("codeExpireTime", expireTime);
        return ResponseUtil.responseSuccess(2, code);

    }

    @ResponseBody
    @RequestMapping("/checkVerifyCode.do")
    public String checkVerifyCode(String code, String phone, String password, HttpServletRequest request, HttpServletResponse response) {
        //response.setContentType("text/html;charset=utf-8");
        //校验时长
        HttpSession session = request.getSession();
        Long expireTime = (Long) session.getAttribute("codeExpireTime");
        long current = System.currentTimeMillis();
        if (current > expireTime) {
            //过期
            return ResponseUtil.responseError(1, "验证码已过期，请重试");
        }
        //校验
        String sessionCode = (String) session.getAttribute("code");
        //校验成功
        if (sessionCode.equals(code)) {
            //注册逻辑
            if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
                return ResponseUtil.responseError(1, "用户名或密码不能为空");
            }
            //插入数据库
            System.out.println("insert in to database");
            return ResponseUtil.responseSuccess(2, "注册成功");

        } else {
            return ResponseUtil.responseError(1, "验证码错误");
        }

    }

    @ResponseBody
    @RequestMapping("/mail.do")
    public String sendMail(String mailAddress) throws CrowdFundingException {
        if (StringUtils.isEmpty(mailAddress)) {
            return ResponseUtil.responseError(1, "邮件地址不能为空");
        }
        String checkUrl = generateMailContent(mailAddress);
        mailService.sendMail(mailAddress, checkUrl);
        //todo 发送成功 记录数据库
        System.out.println("insert int db");
        return ResponseUtil.responseSuccess(2, "success");
    }

    @ResponseBody
    @RequestMapping("/checkMail.do")
    public String checkMail(String mailAddress) {
        if (StringUtils.isEmpty(mailAddress)) {
            return ResponseUtil.responseError(1, "邮件地址不能为空");
        }
        boolean flag = false;
        //todo 数据库查询
        System.out.println("find db");
        if (true) {
            //todo 修改数据ku状态
            return ResponseUtil.responseSuccess(2, "注册成功");
        } else {
            return ResponseUtil.responseError(1, "注册失败");
        }

    }


    private String generateMailContent(String mailAddress) {
        String url = "http://localhost:8080/checkMail.do?mailAddress=";
        byte[] digest = DigestUtils.md5Digest(mailAddress.getBytes());
        String md5Addr = new String(digest);
        return url + md5Addr;
    }


    private String generateVerifyCode(int size) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int num = random.nextInt(9);
            builder.append(num);
        }
        return builder.toString();
    }


    @RequestMapping("/realCheck.do")
    @ResponseBody
    public String realCheck(String phone, String idCard, HttpServletRequest request) throws CrowdFundingException {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
            if (!user.getPhone().equals(phone)){
                throw new CrowdFundingException(2,"请填写注册时使用的手机号");
            }

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            String idCardPositiveUrl = "";
            String idCardNegativeUrl = "";
            String idCardHandUrl = "";

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    System.out.println(file.getName());
                    try {
                        if (file.getName().contains("idCardPositive")) {
                            //上传身份证正面
                            String idCardPositive = UUID.randomUUID().toString() + ".jpeg";
                            idCardPositiveUrl = ossService.upload("crowdfundingweb", idCardPositive, file.getInputStream());
                        }
                        if (file.getName().contains("idCardNegative")) {
                            //上传身份证正面
                            String idCardNegative = UUID.randomUUID().toString() + ".jpeg";
                            idCardNegativeUrl=ossService.upload("crowdfundingweb", idCardNegative, file.getInputStream());
                        }
                        if (file.getName().contains("idCardHand")) {
                            //上传身份证正面
                            String idCardHand = UUID.randomUUID().toString() + ".jpeg";
                            idCardHandUrl=ossService.upload("crowdfundingweb", idCardHand, file.getInputStream());
                        }
                    } catch (Exception e) {
                        throw new CrowdFundingException(2, "上传失败");
                    }

                }

            }
            // 插入数据库
            RealCheckEntity realCheckEntity = new RealCheckEntity(0, phone, idCard, idCardPositiveUrl, idCardNegativeUrl, idCardHandUrl, 0);
            int rows = realCheckService.insert(realCheckEntity);
            if (rows == 1) {
                return ResponseUtil.responseSuccess(1, "申请成功等待审核");
            } else {
                return ResponseUtil.responseError(2, "申请失败，请重试");
            }

        }
        return ResponseUtil.responseError(2, "请上传图片");

    }


    //获取请求中所有的file


}
