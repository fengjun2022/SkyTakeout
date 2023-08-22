package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;


    @PostMapping("/upload")
    public Result<String> upload (MultipartFile file){

        String originaFileName = file.getOriginalFilename();

        // 截取原始文件名后缀
      String extension =  originaFileName.substring(originaFileName.lastIndexOf("."));
        String objFileName = UUID.randomUUID().toString() + extension;
        log.info("文件上传"+ objFileName);

        try {
       String filePath =   aliOssUtil.upload(file.getBytes(),objFileName );



            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败");
            throw new RuntimeException(e);
        }
    }

}
