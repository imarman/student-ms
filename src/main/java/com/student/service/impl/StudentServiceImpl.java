package com.student.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.common.BusinessException;
import com.student.common.ResultCodeEnum;
import com.student.mapper.StudentMapper;
import com.student.model.Student;
import com.student.model.req.StuLoginReqModel;
import com.student.model.req.StudentReqModel;
import com.student.model.resp.StudentResponse;
import com.student.service.CollegeService;
import com.student.service.MajorService;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @date 2021/12/15 23:17
 */
@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Resource
    CollegeService collegeService;

    @Resource
    MajorService majorService;

    @Override
    public StudentResponse selectByWrapper(Long current, Long limit, StudentReqModel reqModel) {
        Page<Student> page = new Page<>(current, limit);
        LambdaQueryWrapper<Student> wrapper = Wrappers.lambdaQuery();
        if (reqModel.getName() != null && StrUtil.isNotBlank(reqModel.getName())) {
            wrapper.like(Student::getName, reqModel.getName());
        }
        if (reqModel.getStudentId() != null) {
            wrapper.eq(Student::getStudentId, reqModel.getStudentId());
        }
        if (reqModel.getCollege() != null && StrUtil.isNotBlank(reqModel.getCollege())) {
            wrapper.eq(Student::getCollege, reqModel.getCollege());
        }
        if (reqModel.getGrade() != null && StrUtil.isNotBlank(reqModel.getGrade())) {
            wrapper.eq(Student::getGrade, reqModel.getGrade());
        }
        if (reqModel.getMajor() != null && StrUtil.isNotBlank(reqModel.getMajor())) {
            wrapper.eq(Student::getMajor, reqModel.getMajor());
        }
        Page<Student> studentPage = getBaseMapper().selectPage(page, wrapper);
        StudentResponse studentResponse = new StudentResponse();
        List<Student> studentList = studentPage.getRecords();
        studentList.forEach(student -> {
            student.setCollegeName(collegeService.getById(student.getCollege()).getName());
            student.setMajorName(majorService.getById(student.getMajor()).getName());
        });
        studentResponse.setStudentList(studentList);
        studentResponse.setPages(studentPage.getPages());
        studentResponse.setTotal(studentPage.getTotal());
        return studentResponse;
    }

    @Override
    public boolean login(StuLoginReqModel loginReqModel) {
        if (loginReqModel.getStudentId() == null || StrUtil.isEmpty(loginReqModel.getPassword())) {
            log.info("????????????- userModel:{}", loginReqModel);
            throw new BusinessException(ResultCodeEnum.LOGIN_FAIL, "???????????????????????????");
        }
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getStudentId, loginReqModel.getStudentId());
        Student student = getBaseMapper().selectOne(queryWrapper);
        if (student == null) {
            log.info("???????????????");
            throw new BusinessException(ResultCodeEnum.USER_NOT_EXIST);
        }

        // ?????????????????????????????????????????????????????????????????????
        String encryptPwd = MD5.create().digestHex16(loginReqModel.getPassword());
        // ???????????????
        if (!StrUtil.equals(encryptPwd, student.getPassword())) {
            log.error("??????:{} ??????????????????", student.getName());
            throw new BusinessException(ResultCodeEnum.BAD_PASSWORD);
        }
        // ????????????
        StpUtil.login(student.getId());

        // ????????? session ????????????????????????
        student.setPassword(null);
        // ??????????????? session ??????????????????????????????
        StpUtil.getSession().set("user", student);
        return true;
    }
}
