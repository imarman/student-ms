package com.student.model.resp;

import com.student.model.Attendance;
import com.student.model.Student;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2021/12/16 21:14
 */
@Data
public class AttendanceResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Attendance> attendanceList;

    /**
     * 总数据
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pages;

}
