package com.ldw.blog.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//七牛服务器的工具类
@Component
public class Qiniutils {

    //七牛云的地址
    public static  final String url = "http://ri3dd2mrv.hn-bkt.clouddn.com/";
    //七牛云的密钥
    @Value("lylK3yjkpal2QLKtb3dEUzr8p6Ed9a873sk-9AJR")
    private  String accessKey;
    @Value("y7m9_r707n0SJYS9_q8Q4WKLvrtpZtBxckPv14kv")
    private  String accessSecretKey;

    public  boolean upload(MultipartFile file, String fileName){

        //构造一个带指定 Region 对象的配置类  选择地区为华南
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String bucket = "ldw-blog-image";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
