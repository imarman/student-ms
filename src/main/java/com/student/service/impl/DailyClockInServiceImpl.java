package com.student.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.mapper.DailyClockInMapper;
import com.student.model.DailyClockIn;
import com.student.model.Student;
import com.student.model.req.ClockInReqModel;
import com.student.model.resp.ClockInResponse;
import com.student.service.DailyClockInService;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public ClockInResponse selectByWrapper(Long current, Long limit, ClockInReqModel reqModel) {
        Page<DailyClockIn> page = new Page<>(current, limit);
        LambdaQueryWrapper<DailyClockIn> wrapper = Wrappers.lambdaQuery();
        if (reqModel.getStudentId() != null && StrUtil.isNotBlank(reqModel.getStudentId())) {
            wrapper.like(DailyClockIn::getStudentId, reqModel.getStudentId());
        }
        if (reqModel.getGmtCreate() != null) {
            log.error(reqModel.getGmtCreate().toString("yyyy-MM-dd"));
            // wrapper.apply("date_format(gmt_create,'%Y-%m-%d') = {0}", "str_to_date(" + reqModel.getGmtCreate().toString("yyyy-MM-dd") + ",'%Y-%m-%d')");
            wrapper.apply("date_format(gmt_create,'%Y-%m-%d') = {0}", reqModel.getGmtCreate().toString("yyyy-MM-dd"));
        }
        wrapper.orderByDesc(DailyClockIn::getGmtCreate);
        Page<DailyClockIn> dailyClockInPage = getBaseMapper().selectPage(page, wrapper);
        ClockInResponse clockInResponse = new ClockInResponse();
        List<DailyClockIn> dailyClockIns = dailyClockInPage.getRecords();
        dailyClockIns.forEach(dailyClockIn -> {
            Student student = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getStudentId, dailyClockIn.getStudentId()));
            dailyClockIn.setStudentName(student.getName());
            dailyClockIn.setGrade(student.getGrade());
        });
        clockInResponse.setDailyClockIns(dailyClockIns);
        clockInResponse.setPages(dailyClockInPage.getPages());
        clockInResponse.setTotal(dailyClockInPage.getTotal());
        return clockInResponse;
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
