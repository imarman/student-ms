package com.student.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 学生表
 */
@Data
@TableName(value = "student")
public class Student implements Serializable {
    /**
     * 唯一ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 学号
     */
    private Long studentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 学院id
     */
    private String collage;

    /**
     * 专业id
     */
    private String major;

    /**
     * 年级
     */
    private String grade;

    /**
     * 入学时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date admissionTime;

    /**
     * 宿舍号
     */
    private String room;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 照片
     */
    private String avatar;

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