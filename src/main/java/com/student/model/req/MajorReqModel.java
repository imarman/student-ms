package com.student.model.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @date 2021/12/18 17:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MajorReqModel extends BasePageModel implements Serializable {

    /**
     * 专业名称
     */
    private String name;

    /**
     * 所属学院
     */
    private String collegeId;

}
