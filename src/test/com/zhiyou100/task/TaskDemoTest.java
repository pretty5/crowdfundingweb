package com.zhiyou100.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-task.xml"})
public class TaskDemoTest {

    @Test
    public void hello() {
        new ClassPathXmlApplicationContext("spring-task.xml");
    }
}