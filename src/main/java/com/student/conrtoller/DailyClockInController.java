package com.student.conrtoller;

import cn.dev33.satoken.stp.StpUtil;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.DailyClockIn;
import com.student.model.req.ClockInReqModel;
import com.student.model.resp.ClockInResponse;
import com.student.model.resp.StudentResponse;
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

    @GetMapping("/list/{current}/{limit}")
    public R list(@PathVariable Long current,
                  @PathVariable Long limit,
                  ClockInReqModel reqModel) {
        log.info("获取所有打卡学生方法执行，参数：reqModel:{}", reqModel);
        if (current == null || limit == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        ClockInResponse clockInResponse = dailyClockInService.selectByWrapper(current, limit, reqModel);
        return R.ok(clockInResponse);
    }
}
