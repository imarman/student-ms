package com.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.model.Apply;
import com.student.model.req.StatusReqModel;
import org.apache.ibatis.annotations.Update;

/**
 * @date 2022/1/1 19:12
 */
public interface ApplyMapper extends BaseMapper<Apply> {

    /**
     * 更新状态
     */
    void updateStatus(StatusReqModel reqModel);
}