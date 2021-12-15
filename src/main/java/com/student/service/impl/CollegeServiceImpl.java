package com.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.mapper.CollegeMapper;
import com.student.model.College;
import com.student.service.CollegeService;
import org.springframework.stereotype.Service;

/**
 * @date 2021/12/15 23:25
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

}
