package com.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.mapper.DailyClockInMapper;
import com.student.model.DailyClockIn;
import com.student.model.Student;
import com.student.service.DailyClockInService;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @date 2022/1/1 12:14
 */
@Service
@Slf4j
public class DailyClockInServiceImpl extends ServiceImpl<DailyClockInMapper, DailyClockIn> implements DailyClockInService {

    @Resource
    StudentService studentService;

    @Override
    public DailyClockIn getTodayInfo(String id) {
        log.info("获取当天的打卡信息方法执行，参数：id:{}", id);
        LambdaQueryWrapper<DailyClockIn> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DailyClockIn::getStudentId, id)
                .orderByDesc(DailyClockIn::getId)
                .last("limit 1");

        DailyClockIn theOne = getBaseMapper().selectOne(lambdaQueryWrapper);
        if (theOne != null && isToday(theOne.getGmtCreate())) {
            Student student = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getStudentId, id));
            theOne.setGrade(student.getGrade());
            theOne.setStudentName(student.getName());
            return theOne;
        }
        log.info("学生(id:{}, 今日未打卡)", id);
        return null;
    }

    private boolean isToday(LocalDateTime localTime) {
        boolean flag = false;
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        //如果大于今天的开始日期，小于今天的结束日期
        if (localTime.isAfter(startTime) && localTime.isBefore(endTime)) {
            flag = true;
        }
        return flag;
    }
}
