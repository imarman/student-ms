package com.student.conrtoller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.Student;
import com.student.model.req.StudentReqModel;
import com.student.model.resp.StudentResponse;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @date 2021/12/16 21:07
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Resource
    StudentService studentService;

    @SaCheckRole(value = {"TEACHER", "ADMIN"}, mode = SaMode.OR)
    @GetMapping("/list/{current}/{limit}")
    public R getAllStudent(@PathVariable Long current,
                           @PathVariable Long limit,
                           StudentReqModel reqModel) {
        log.info("根据条件获取所有学生方法执行，参数：reqModel:{}", reqModel);
        if (current == null || limit == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        StudentResponse studentResponse = studentService.selectByWrapper(current, limit, reqModel);
        return R.ok(studentResponse);
    }

    @GetMapping("/all")
    public R getAll() {
        return R.ok(studentService.list());
    }

    @GetMapping("/getById")
    public R getById(String id) {
        log.info("根据id获取学生方法执行，参数：id:{}", id);
        return R.ok(studentService.getById(id));
    }

    @PostMapping("/saveOrUpdate")
    public R saveOrUpdateStudent(@RequestBody Student student) {
        log.info("新增或修改学生方法执行，参数：student:{}", student);
        if (student == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        if (student.getId() == null || StrUtil.isBlank(student.getId())) {
            student.setGmtCreate(new Date());
        }
        String MD5Pwd = MD5.create().digestHex16("123");
        if (StrUtil.isNotBlank(student.getPassword())) {
            MD5Pwd = MD5.create().digestHex16(student.getPassword());
        }
        student.setPassword(MD5Pwd);
        student.setGmtModified(new Date());
        return studentService.saveOrUpdate(student) ? R.ok() : R.error();
    }

    @DeleteMapping("/delete/{id}")
    public R deleteStudent(@PathVariable("id") String studentId) {
        log.info("删除学生方法执行，参数：studentId:{}", studentId);
        if (studentId == null || StrUtil.isBlank(studentId)) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        return studentService.removeById(studentId) ? R.ok() : R.error();
    }

}
