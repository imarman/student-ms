package com.student.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.common.BusinessException;
import com.student.common.ResultCodeEnum;
import com.student.mapper.StudentMapper;
import com.student.model.Student;
import com.student.model.req.StudentReqModel;
import com.student.model.resp.StudentResponse;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @date 2021/12/15 23:17
 */
@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public StudentResponse selectByWrapper(StudentReqModel reqModel) {
        if (reqModel.getCurrent() == null || reqModel.getLimit() == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        Page<Student> page = new Page<>(reqModel.getCurrent(), reqModel.getLimit());
        LambdaQueryWrapper<Student> wrapper = Wrappers.lambdaQuery();
        if (reqModel.getName() != null && StrUtil.isNotBlank(reqModel.getName())) {
            wrapper.like(Student::getName, reqModel.getName());
        }
        if (reqModel.getStudentId() != null) {
            wrapper.eq(Student::getStudentId, reqModel.getStudentId());
        }
        if (reqModel.getCollage() != null || StrUtil.isNotBlank(reqModel.getCollage())) {
            wrapper.eq(Student::getCollage, reqModel.getCollage());
        }
        if (reqModel.getGrade() != null || StrUtil.isNotBlank(reqModel.getGrade())) {
            wrapper.eq(Student::getGrade, reqModel.getGrade());
        }
        if (reqModel.getMajor() != null || StrUtil.isNotBlank(reqModel.getMajor())) {
            wrapper.eq(Student::getMajor, reqModel.getMajor());
        }
        Page<Student> studentPage = getBaseMapper().selectPage(page, wrapper);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setStudentList(studentPage.getRecords());
        studentResponse.setPages(studentPage.getPages());
        studentResponse.setTotal(studentPage.getTotal());
        return studentResponse;
    }
}
