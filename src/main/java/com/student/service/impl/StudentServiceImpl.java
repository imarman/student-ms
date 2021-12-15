package com.student.service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.mapper.StudentMapper;
import com.student.model.Student;
import com.student.service.StudentService;

/**
 * @date 2021/12/15 23:17
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
