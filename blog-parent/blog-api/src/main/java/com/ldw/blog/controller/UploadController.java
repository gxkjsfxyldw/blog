package com.ldw.blog.controller;

import com.ldw.blog.utils.Qiniutils;
import com.ldw.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

//图片上传 至服务器
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private Qiniutils qiniutils;

    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file){//MultipartFile是spring 中专门用于接受文件的
        //原始文件名称 比如，a.jpg
        String originalFilename = file.getOriginalFilename();
        //唯一的文件名称
        String filename = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        //上传文件，上传到哪呢  上传到七牛云 云服务器，按量付费，速度快 把图片发放到用户最近的服务器上，速度比较快
        //降低 自身应用服务器的带宽消耗
        boolean upload = qiniutils.upload(file, filename);
        if(upload){//如果上传成功就返回一个文件上传成功的地址
            System.out.println("图片的地址："+Qiniutils.url+filename);
            return Result.success(Qiniutils.url+filename);
        }
        return Result.fail(20001,"文件上传失败");
    }
}
