package com.zhiyou100.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/*
*@ClassName:OSSService
 @Description:TODO
 @Author:
 @Date:2018/9/18 14:20 
 @Version:v1.0
*/
@Service
public class OSSService {
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    static final String accessKeyId = "LTAIyrDJ8IbGvsln";
    static final String accessKeySecret = "hnSMoKj41dhGZd64WVmXmN3G3Iv2wc";

    public String upload(String bucketName,String fileName,InputStream inputStream)  {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。

        PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
        return url;
    }
    public void downLoad(String bucketName,String objectName,String target){
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(target));
// 关闭OSSClient。
        ossClient.shutdown();
    }
}
