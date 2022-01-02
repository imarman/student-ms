package com.student.model.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @date 2021/1/1 17:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplyReqModel extends BasePageModel implements Serializable {

    /**
     * 学生学号
     */
    private String studentId;

    /**
     * 类型
     */
    private String type;

    /**
     * 状态
     */
    private String status;

}
