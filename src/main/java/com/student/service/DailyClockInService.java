package com.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.model.DailyClockIn;

/**
 * @date 2022/1/1 12:14
 */
public interface DailyClockInService extends IService<DailyClockIn> {

    /**
     * 获取当天的打卡信息
     * @param id  学生id
     * @return 打卡对象
     */
    DailyClockIn getTodayInfo(String id);
}
