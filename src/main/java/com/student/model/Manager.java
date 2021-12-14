package com.student.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    @date 2021/12/14 20:48
*/

/**
 * 管理者
 */
@Data
@TableName(value = "manager")
public class Manager implements Serializable {
    /**
     * 唯一ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 工号
     */
    @TableField(value = "work_id")
    private Long workId;

    /**
     * 用户手机
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 头像文件
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 注册时间
     */
    @TableField(value = "reg_time")
    private Date regTime;

    /**
     * 用户状态(正常:0, 未激活:1, 冻结:2)
     */
    @TableField(value = "`status`")
    private Boolean status;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified")
    private Date gmtModified;

    /**
     * 角色
     */
    @TableField(value = "`role`")
    private String role;

    /**
     * 是否是系统管理员 1yes 0 no
     */
    @TableField(value = "root")
    private Boolean root;

    private static final long serialVersionUID = 1L;
}