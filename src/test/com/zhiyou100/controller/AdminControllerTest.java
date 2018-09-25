package com.zhiyou100.controller;

import com.zhiyou100.entity.AdminEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mvc.xml"})
@WebAppConfiguration
public class AdminControllerTest {
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void manualRealCheck() throws Exception {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setId(2);

        mockMvc.perform(post("/admin/manualRealCheck.do")
        .sessionAttr("admin",adminEntity)
        .param("result","success")
        .param("realCheckId","1"))
        .andDo(print());

    }

    @Test
    public void unRealCheckedList() throws Exception {
        MvcResult result = mockMvc.perform(post("/admin/unRealCheckedList.do").param("page", "1"))
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);

    }
}