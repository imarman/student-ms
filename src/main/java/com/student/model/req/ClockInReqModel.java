package com.student.model.req;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @date 2021/12/18 17:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClockInReqModel extends BasePageModel implements Serializable {

    /**
     * 专业名称
     */
    private String studentId;

    /**
     * 打卡时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private DateTime gmtCreate;

}
