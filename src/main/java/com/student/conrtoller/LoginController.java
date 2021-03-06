package com.student.conrtoller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.common.RoleConst;
import com.student.model.Manager;
import com.student.model.Student;
import com.student.model.req.LoginReqModel;
import com.student.model.req.StuLoginReqModel;
import com.student.service.ManagerService;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @date 2021/12/14 20:49
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class LoginController {

    @Resource
    ManagerService managerService;

    @Resource
    StudentService studentService;

    @PostMapping("/login")
    public R login(@RequestBody LoginReqModel loginReqModel) {
        boolean res = managerService.login(loginReqModel);
        if (res) {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return R.ok(tokenInfo);
        }
        return R.error("登陆失败");
    }

    @PostMapping("/student-login")
    public R studentLogin(@RequestBody StuLoginReqModel loginReqModel) {
        boolean res = studentService.login(loginReqModel);
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
        HashMap<String, Object> userInfo = new HashMap<>();
        if (manager != null) {
            userInfo.put("name", manager.getUsername());
            userInfo.put("role", manager.getRole());
            userInfo.put("avatar", manager.getAvatar());
            userInfo.put("id", manager.getId());
            return R.ok(userInfo);
        }
        Student student = studentService.getById(userID);
        userInfo.put("name", student.getName());
        userInfo.put("role", RoleConst.STUDENT);
        userInfo.put("avatar", student.getAvatar());
        userInfo.put("id", student.getId());
        return R.ok(userInfo);
    }


}
