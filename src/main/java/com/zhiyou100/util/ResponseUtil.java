package com.zhiyou100.util;

import com.google.gson.Gson;

/*
*@ClassName:ResponseUtil
 @Description:TODO
 @Author:
 @Date:2018/9/17 10:00 
 @Version:v1.0
*/
public class ResponseUtil {
    public static<T> String responseSuccess(int code,T data){
        Response<T> response = new Response<>(data, code, null);
        return new Gson().toJson(response);
    }
    public static<T> String responseError(int code,String error){
        Response<T> response = new Response<>(null, code, error);
        return new Gson().toJson(response);
    }
}
