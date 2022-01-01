package com.student.conrtoller;

import cn.dev33.satoken.stp.StpUtil;
import com.student.common.R;
import com.student.model.DailyClockIn;
import com.student.service.DailyClockInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @date 2022/1/1 12:21
 */
@RestController
@RequestMapping("/daily")
@Slf4j
public class DailyClockInController {

    @Resource
    DailyClockInService dailyClockInService;

    @GetMapping("/get-today-info")
    public R getTodayInfo(String studentId) {
        DailyClockIn res = dailyClockInService.getTodayInfo(studentId);
        return R.ok(res);
    }
    
    @PostMapping("/save")
    public R save(@RequestBody DailyClockIn dailyClockIn) {
        log.info("保存每日健康打卡方法执行，参数：dailyClockIn:{}", dailyClockIn);
        dailyClockIn.setGmtCreate(LocalDateTime.now());
        dailyClockIn.setGmtModified(LocalDateTime.now());
        boolean save = dailyClockInService.save(dailyClockIn);
        return save ? R.ok("打卡成功") : R.error("打卡失败,请联系管理员");
    }
}
