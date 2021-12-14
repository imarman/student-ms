package com.student.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


public interface FileUploadService {

    /**
     * 文件上传
     * @param file 文件兑现
     * @return 地址
     */
    String uploadFile(MultipartFile file, HttpServletRequest request);

}
