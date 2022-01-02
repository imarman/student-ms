package com.student.model.resp;

import com.student.model.Apply;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2021/1/1 21:14
 */
@Data
public class ApplyingResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Apply> applyList;

    /**
     * 总数据
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pages;

}
