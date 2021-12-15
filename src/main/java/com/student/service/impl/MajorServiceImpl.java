package com.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.mapper.MajorMapper;
import com.student.model.Major;
import com.student.service.MajorService;
import org.springframework.stereotype.Service;

/**
 * @date 2021/12/15 23:23
 */
@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

}
