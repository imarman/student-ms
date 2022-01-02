package com.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.mapper.ApplyMapper;
import com.student.model.Apply;
import com.student.model.req.StatusReqModel;
import com.student.service.ApplyService;
import org.springframework.stereotype.Service;

/**
 * @date 2022/1/1 19:12
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {

    @Override
    public void updateState(StatusReqModel reqModel) {
        getBaseMapper().updateStatus(reqModel);
    }

}
