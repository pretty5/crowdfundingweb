package com.zhiyou100.task;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/*
*@ClassName:TaskDemo
 @Description:TODO
 @Author:
 @Date:2018/9/18 9:57 
 @Version:v1.0
*/
@Component
public class TaskDemo {
    public static void main(String[] args) {

        new ClassPathXmlApplicationContext("spring-task.xml");
    }
    public void hello(){

        System.out.println("hello");


    }
    public void world(){

        System.out.println("world");


    }
}
