package com.student.config;


import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.student.model.Manager;
import com.student.service.ManagerService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Configuration
public class StpInterfaceImpl implements StpInterface {

    @Resource
    ManagerService sysUserService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>();
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String id = (String) StpUtil.getLoginId();
        ArrayList<String> strings = new ArrayList<>();
        Manager sysUser = sysUserService.getById(id);
        if (sysUser != null) {
            strings.add(sysUser.getRole());
            return strings;
        }
        strings.add("STUDENT");
        return strings;
    }

}