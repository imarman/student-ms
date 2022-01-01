package com.student.conrtoller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.student.common.ApplyingConst;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.Apply;
import com.student.model.Student;
import com.student.service.ApplyService;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @date 2022/1/1 19:15
 */
@RestController
@RequestMapping("/applying")
@Slf4j
public class ApplyingController {

    @Resource
    ApplyService applyService;

    @Resource
    StudentService studentService;

    @GetMapping("/list")
    public R getAll() {
        return R.ok(applyService.list());
    }

    @GetMapping("/my-list")
    public R myAllApplying(String studentId, String type) {
        log.info("获取学生的申请方法执行，参数：studentId:{}, type:{}", studentId, type);
        if (studentId == null || StrUtil.isBlank(studentId)) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        LambdaQueryWrapper<Apply> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Apply::getStuId, studentId);
        wrapper.eq(Apply::getType, type);
        List<Apply> applies = applyService.list(wrapper);
        if (!applies.isEmpty()) {
            Student student = studentService.getById(applies.get(0).getStuId());
            applies.forEach(apply -> apply.setStudentName(student.getName()));
        }
        return R.ok(applies);
    }

    @PostMapping("/save")
    public R save(@RequestBody Apply apply) {
        log.info("保存申请方法执行，参数：apply:{}", apply);
        apply.setGmtCreate(new Date());
        apply.setGmtModified(new Date());
        apply.setStatus(ApplyingConst.APPLYING);
        return applyService.save(apply) ? R.ok() : R.error();
    }

    @DeleteMapping("/delete")
    public R delete(String id) {
        log.info("删除申请方法执行，参数：id:{}", id);
        return applyService.removeById(id) ? R.ok() : R.error();
    }
}