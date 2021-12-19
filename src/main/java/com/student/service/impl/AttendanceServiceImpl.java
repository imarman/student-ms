package com.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.mapper.AttendanceMapper;
import com.student.model.Attendance;
import com.student.service.AttendanceService;
import org.springframework.stereotype.Service;

/**
 * @date 2021/12/19 18:42
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

}
