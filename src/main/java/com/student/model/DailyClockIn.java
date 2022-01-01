package com.student.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @date 2022/1/1 12:14
 */
@Data
@TableName(value = "daily_clock_in")
public class DailyClockIn implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 学生id
     */
    private String studentId;

    @TableField(exist = false)
    private String studentName;

    @TableField(exist = false)
    private String grade;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 早上体温
     */
    private Double morning;

    /**
     * 中午体温
     */
    private Double moon;

    /**
     * 晚上体温
     */
    private Double night;

    /**
     * 其他不适
     */
    private String other;

    /**
     * 所在位置
     */
    private String region;

    /**
     * 创建时间
     */
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    private static final long serialVersionUID = 1L;
}