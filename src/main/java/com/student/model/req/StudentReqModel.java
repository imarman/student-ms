package com.student.model.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @date 2021/12/16 21:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentReqModel extends BasePageModel implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 学号
     */
    private Long studentId;

    /**
     * 学院id
     */
    private String college;

    /**
     * 专业id
     */
    private String major;

    /**
     * 年级
     */
    private String grade;

}
