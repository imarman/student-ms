package com.student.model.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @date 2022/1/2 14:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StatusReqModel extends BasePageModel implements Serializable {

    private String id;

    private String status;
}
