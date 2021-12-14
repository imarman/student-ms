package com.student.conrtoller;

import com.student.common.R;
import com.student.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {


    @Resource
    FileUploadService fileUploadService;

    @PostMapping()
    public R doUpload(MultipartFile file, HttpServletRequest request) {
        String filePath = fileUploadService.uploadFile(file, request);
        Map<String, String> map = new HashMap<>();
        map.put("picUrl", filePath);
        return R.ok(map);
    }

}
