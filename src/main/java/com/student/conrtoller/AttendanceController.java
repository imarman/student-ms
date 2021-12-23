package com.student.conrtoller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.Attendance;
import com.student.model.Student;
import com.student.model.resp.AttendanceResponse;
import com.student.service.AttendanceService;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @date 2021/12/19 18:44
 */
@RestController
@RequestMapping("/attendance")
@Slf4j
public class AttendanceController {

    @Resource
    AttendanceService attendanceService;

    @Resource
    StudentService studentService;

    @GetMapping("/list/{current}/{limit}")
    public R getAll(@PathVariable Long current,
                    @PathVariable Long limit,
                    String studentId) {
        log.info("获取所有考勤记录方法执行，参数：current:{}, limit:{}, studentId:{}", current, limit, studentId);
        if (limit == null || current == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        LambdaQueryWrapper<Attendance> wrapper = null;
        if (studentId != null && StrUtil.isNotBlank(studentId)) {
            wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Attendance::getStudentId, studentId);
        }
        Page<Attendance> attendancePage = attendanceService.page(new Page<>(current, limit), wrapper);
        List<Attendance> records = attendancePage.getRecords();

        records.forEach(attendance -> {
            if (attendance.getStudentId() != null && StrUtil.isNotBlank(attendance.getStudentId())) {
                LambdaQueryWrapper<Student> queryWrapper = Wrappers.lambdaQuery();
                Student student = studentService.getOne(queryWrapper.eq(Student::getStudentId, attendance.getStudentId()));
                if (student != null){
                    attendance.setStudentName(student.getName());
                }
            }
        });
        AttendanceResponse response = new AttendanceResponse();
        response.setAttendanceList(records);
        response.setPages(attendancePage.getPages());
        response.setTotal(attendancePage.getTotal());
        return R.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") String id) {
        log.info("删除考勤记录方法执行，参数：id:{}", id);
        return attendanceService.removeById(id) ? R.ok() : R.error();
    }

    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Attendance attendance) {
        log.info("保存或更新考勤方法执行，参数：attendance:{}", attendance);
        if (attendance == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        if (attendance.getId() == null || StrUtil.isBlank(attendance.getId())) {
            attendance.setGmtCreate(new Date());
        }
        attendance.setGmtModified(new Date());
        return attendanceService.saveOrUpdate(attendance) ? R.ok() : R.error();

    }

}
