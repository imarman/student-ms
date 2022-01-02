package com.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.model.Apply;
import com.student.model.req.StatusReqModel;

/**
 * @date 2022/1/1 19:12
 */
public interface ApplyService extends IService<Apply> {

    void updateState(StatusReqModel reqModel);

}
