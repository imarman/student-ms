package com.student.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 专业
 */
@Data
@TableName(value = "major")
public class Major implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 所属学院
     */
    private String collegeId;

    /**
     * 建立时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date buildTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}