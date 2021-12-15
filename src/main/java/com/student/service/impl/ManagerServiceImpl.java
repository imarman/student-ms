package com.student.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.common.BusinessException;
import com.student.common.ResultCodeEnum;
import com.student.mapper.ManagerMapper;
import com.student.model.Manager;
import com.student.model.req.LoginReqModel;
import com.student.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @date 2021/12/14 20:48
 */
@Service
@Slf4j
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Override
    public boolean login(LoginReqModel loginReqModel) {
        if (loginReqModel.getWorkId() == null || StrUtil.isEmpty(loginReqModel.getPassword())) {
            log.info("参数缺失- userModel:{}", loginReqModel);
            throw new BusinessException(ResultCodeEnum.LOGIN_FAIL, "邮箱或密码不能为空");
        }
        LambdaQueryWrapper<Manager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Manager::getWorkId, loginReqModel.getWorkId());
        Manager manager = getBaseMapper().selectOne(queryWrapper);
        if (manager == null) {
            log.info("用户不存在");
            throw new BusinessException(ResultCodeEnum.USER_NOT_EXIST);
        }

        // 拿到数据库用户的盐值，和输入的密码做加密后对比
        String encryptPwd = MD5.create().digestHex16(loginReqModel.getPassword());
        // 账号不匹配
        if (!StrUtil.equals(encryptPwd, manager.getPassword())) {
            log.error("用户:{} 输入密码错误", manager.getUsername());
            throw new BusinessException(ResultCodeEnum.BAD_PASSWORD);
        }
        // 账号匹配
        StpUtil.login(manager.getId());

        // 存入到 session 时要屏蔽敏感数据
        manager.setPassword(null);
        // 把用户存到 session 中，方便取出用户信息
        StpUtil.getSession().set("manager", manager);
        return true;
    }
}

