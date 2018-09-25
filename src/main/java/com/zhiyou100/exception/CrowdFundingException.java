package com.zhiyou100.exception;

/*
*@ClassName:CrowdFundingException
 @Description:TODO
 @Author:
 @Date:2018/9/18 17:19 
 @Version:v1.0
*/
/*如果属于实现的异常类，通过定义的异常类（控制层的异常类），抛出此类异常*/
public class CrowdFundingException extends Exception {

    private int code;
    private String error;

    public CrowdFundingException(int code,String error){
        this.code=code;
        this.error=error;
    }

    @Override
    public String getMessage() {
        return this.error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
