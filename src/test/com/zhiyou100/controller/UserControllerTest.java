package com.zhiyou100.controller;

import com.google.gson.Gson;
import com.zhiyou100.util.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mvc.xml"})
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    //@Test
    public void getVerifyCode() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/user/verifyCode.do")
                .param("phone", "17788169512"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseString = mvcResult.getResponse().getContentAsString();
        Response response = new Gson().fromJson(responseString, Response.class);
        assertTrue(response.getCode() == 2);
        System.out.println(response);

    }

    @Test
    public void checkVerifyCode() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/user/checkVerifyCode.do")
                        .param("phone", "17788169512")
                        .param("password", "123")
                        .param("code", "1234")
                        .sessionAttr("code", "1234")
                        .sessionAttr("codeExpireTime", System.currentTimeMillis()+1000000))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mvcResultResponse = mvcResult.getResponse();
        mvcResultResponse.setContentType("text/plain;charset=utf8");
        String responseString = mvcResultResponse.getContentAsString();
        Response response = new Gson().fromJson(responseString, Response.class);
        assertTrue(response.getCode() == 2);
        System.out.println(response);

    }

    @Test
    public void realCheck() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile("idCardPositive.jpeg", "do.jpeg", "image/jpeg", new FileInputStream("C:\\Users\\root\\Desktop\\images\\idCardPositive.jpeg"));
        MockMultipartFile file2 = new MockMultipartFile("idCardNegative.jpeg", "do.jpeg", "image/jpeg", new FileInputStream("C:\\Users\\root\\Desktop\\images\\idCardNegative.jpeg"));
        MockMultipartFile file3 = new MockMultipartFile("idCardHand.jpeg", "do.jpeg", "image/jpeg", new FileInputStream("C:\\Users\\root\\Desktop\\images\\idCardHand.jpeg"));

        MvcResult mvcResult = mockMvc
                .perform(fileUpload("/user/realCheck.do")
                        .file(file1)
                .file(file2)
                .file(file3).param("idCard","422112231312312").param("phone","123123123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void ex() throws Exception {
        mockMvc.perform(post("/user/ex.do"))
                .andDo(print());

    }
}

