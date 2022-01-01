package com.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.model.DailyClockIn;
import com.student.model.req.ClockInReqModel;
import com.student.model.resp.ClockInResponse;

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

    /**
     * 根据条件查询所有打卡学生
     * @param current 当前页
     * @param limit limit
     * @param reqModel 条件对象
     * @return 响应对象
     */
    ClockInResponse selectByWrapper(Long current, Long limit, ClockInReqModel reqModel);
}
