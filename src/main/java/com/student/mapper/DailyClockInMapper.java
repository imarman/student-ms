package com.student.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.model.DailyClockIn;

/**
 * @date 2022/1/1 12:14
 */
public interface DailyClockInMapper extends BaseMapper<DailyClockIn> {

    Page<DailyClockIn> customSelect(Page<DailyClockIn> page, LambdaQueryWrapper<DailyClockIn> wrapper);
}