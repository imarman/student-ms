package com.student.service;

import com.student.model.Manager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student.model.req.LoginReqModel;

/**
    @date 2021/12/14 20:48
*/
public interface ManagerService extends IService<Manager>{

    /**
     * 登陆
     * @param loginReqModel
     * @return
     */
    boolean login(LoginReqModel loginReqModel);
    }
