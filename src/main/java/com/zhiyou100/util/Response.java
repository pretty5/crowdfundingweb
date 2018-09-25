package com.zhiyou100.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
*@ClassName:Response
 @Description:TODO
 @Author:
 @Date:2018/9/17 9:55 
 @Version:v1.0
*/
@Data
@AllArgsConstructor
public class Response<T> {
    private T data;
    private int code;
    private String error;
}
