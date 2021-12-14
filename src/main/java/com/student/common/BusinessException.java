package com.student.common;

import lombok.Getter;
import lombok.ToString;

/**
 * @date 2021/12/14 16:00
 */
@Getter
@ToString
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 5227069401135182444L;

    private ResultCodeEnum resultCodeEnum;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.resultCodeEnum = resultCodeEnum;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum, String message) {
        super(message);
        this.resultCodeEnum = resultCodeEnum;
    }
}
