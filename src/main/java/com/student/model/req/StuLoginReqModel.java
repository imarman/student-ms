package com.student.model.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @date 2021/12/10 16:06
 */
@Data
public class StuLoginReqModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 工号
     */
    private Long studentId;


}
