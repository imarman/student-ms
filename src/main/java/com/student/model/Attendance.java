package com.student.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤表
 *
 * @date 2021/12/19 18:42
 */
@Data
@TableName(value = "attendance")
public class Attendance implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 学生id
     */
    private String studentId;

    /**
     * 学生姓名
     */
    @TableField(exist = false)
    private String studentName;

    /**
     * 课程名
     */
    private String className;

    /**
     * 逃课时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date runTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}