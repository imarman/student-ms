package com.student.conrtoller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.Manager;
import com.student.model.req.LoginReqModel;
import com.student.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @date 2021/12/14 20:49
 */
@RestController("/sys")
@Slf4j
public class LoginController {

    @Resource
    ManagerService managerService;

    @PostMapping("/login")
    public R login(@RequestBody LoginReqModel loginReqModel) {

        boolean res = managerService.login(loginReqModel);
        if (res) {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return R.ok(tokenInfo);
        }
        return R.error("登陆失败");
    }

    @GetMapping("/logout")
    public R logout() {
        if (!StpUtil.isLogin()) {
            return R.errorMsg(ResultCodeEnum.LOGIN_AUTH, "请重新登录!");
        }
        StpUtil.logout(StpUtil.getLoginId());
        return R.ok("成功退出");
    }

    /**
     * 获取用户信息
     */
    @SaCheckLogin
    @GetMapping("/sysUser/getInfo")
    public R getInfo() {
        log.info("获取用户信息");
        String userID = (String) StpUtil.getLoginId();
        Manager manager = managerService.getById(userID);
        HashMap<String,Object> userInfo = new HashMap<>();
        userInfo.put("name", manager.getUsername());
        userInfo.put("role", manager.getRole());
        userInfo.put("avatar", manager.getAvatar());
        userInfo.put("id", manager.getId());
        return R.ok(userInfo);
    }


}
