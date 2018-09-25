package com.zhiyou100.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml"})
public class ShortMessageServiceTest {
    @Autowired
    ShortMessageService shortMessageService;

    @Test
    public void sendVerifyCode() throws ClientException {
        SendSmsResponse response = shortMessageService.sendVerifyCode("17788169512", "12341234");

        System.out.println(response);
    }
}