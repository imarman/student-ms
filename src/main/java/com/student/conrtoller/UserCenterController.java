package com.student.conrtoller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.common.RoleConst;
import com.student.model.Manager;
import com.student.model.Student;
import com.student.model.req.ChangePwdReqModel;
import com.student.service.CollegeService;
import com.student.service.MajorService;
import com.student.service.ManagerService;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @date 2021/12/19 11:18
 */
@RestController
@RequestMapping("user-center")
@Slf4j
public class UserCenterController {

    @Resource
    ManagerService managerService;

    @Resource
    StudentService studentService;

    @Resource
    MajorService majorService;

    @Resource
    CollegeService collegeService;

    @GetMapping("/manager/userinfo")
    public R getInfo(String id) {
        log.info("获取管理员用户信息方法执行，参数：id:{}", id);
        Manager user = managerService.getById(id);
        user.setPassword(null);
        return R.ok(user);
    }

    @PutMapping("/changeManagerInfo")
    public R changeInfo(@RequestBody Manager manager) {
        log.info("更改管理员信息方法执行，参数：manager:{}", manager);
        manager.setGmtModified(new Date());
        return managerService.updateById(manager) ? R.ok() : R.error();
    }

    @GetMapping("/student/userinfo")
    public R getStuInfo(String id) {
        log.info("获取学生信息方法执行，参数：id:{}", id);
        Student user = studentService.getById(id);
        user.setPassword(null);
        user.setCollegeName(collegeService.getById(user.getCollege()).getName());
        user.setMajorName(majorService.getById(user.getMajor()).getName());
        return R.ok(user);
    }

    @PutMapping("/changeStudentInfo")
    public R changeStuInfo(@RequestBody Student student) {
        log.info("更改学生信息方法执行，参数：student:{}", student);
        student.setGmtModified(new Date());
        return studentService.updateById(student) ? R.ok() : R.error();
    }

    @PutMapping("/changePwd")
    public R changePwd(@RequestBody ChangePwdReqModel reqModel) {
        log.info("更改密码方法执行，参数：reqModel:{}", reqModel);

        if(RoleConst.STUDENT.equals(reqModel.getRole())) {
            Student student = studentService.getById(reqModel.getId());
            // 对输入的老密码进行加密
            String encryptOldPwd = MD5.create().digestHex16(reqModel.getOldPassword());
            if (!StrUtil.equals(student.getPassword(), encryptOldPwd)) {
                throw new BusinessException(ResultCodeEnum.PARAMS_MISSING, "旧密码错误，请重新输入");
            }
            student.setPassword(MD5.create().digestHex16(reqModel.getNewPassword()));
            log.info("学生密码修改成功！");
            return studentService.updateById(student) ? R.ok() : R.error();
        }
        Manager manager = managerService.getById(reqModel.getId());
        // 对输入的老密码进行加密
        String encryptOldPwd = MD5.create().digestHex16(reqModel.getOldPassword());
        if (!StrUtil.equals(manager.getPassword(), encryptOldPwd)) {
            throw new BusinessException(ResultCodeEnum.PARAMS_MISSING, "旧密码错误，请重新输入");
        }
        manager.setPassword(MD5.create().digestHex16(reqModel.getNewPassword()));
        log.info("管理人员密码修改成功！");
        return managerService.updateById(manager) ? R.ok() : R.error();
    }

}
