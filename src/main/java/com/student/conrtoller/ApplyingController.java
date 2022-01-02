package com.student.conrtoller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.common.ApplyingConst;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.Apply;
import com.student.model.Student;
import com.student.model.req.ApplyReqModel;
import com.student.model.req.StatusReqModel;
import com.student.model.resp.ApplyingResponse;
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

    @SaCheckRole(value = {"TEACHER", "ADMIN"}, mode = SaMode.OR)
    @GetMapping("/list/{current}/{limit}")
    public R getAll(@PathVariable Long current,
                    @PathVariable Long limit,
                    ApplyReqModel reqModel) {
        log.info("获取所有申请方法执行，参数：current:{}, limit:{}, reqModel:{}", current, limit, reqModel);
        Page<Apply> applyPage = new Page<>(current, limit);
        LambdaQueryWrapper<Apply> wrapper = Wrappers.lambdaQuery();
        if (reqModel.getStudentId() != null && StrUtil.isNotBlank(reqModel.getStudentId())) {
            Student one = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getStudentId, reqModel.getStudentId()));
            if (one != null) {
                wrapper.eq(Apply::getStuId, one.getId());
            } else {
                wrapper.eq(Apply::getStuId, 0);
            }
        }
        if (reqModel.getType() != null && StrUtil.isNotBlank(reqModel.getType())) {
            wrapper.eq(Apply::getType, reqModel.getType());
        }
        if (reqModel.getStatus() != null && StrUtil.isNotBlank(reqModel.getStatus())) {
            wrapper.eq(Apply::getStatus, reqModel.getStatus());
        }
        wrapper.orderByDesc(Apply::getGmtCreate);
        Page<Apply> resPage = applyService.page(applyPage, wrapper);
        ApplyingResponse response = new ApplyingResponse();
        response.setPages(resPage.getPages());
        response.setTotal(resPage.getTotal());
        List<Apply> records = resPage.getRecords();
        records.forEach(apply -> {
            Student byId = studentService.getById(apply.getStuId());
            apply.setStudentName(byId.getName());
            apply.setStudentId(byId.getStudentId());
        });
        response.setApplyList(records);
        return R.ok(response);
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

    @PutMapping("/changeStatus")
    public R changeStatus(@RequestBody StatusReqModel reqModel) {
        log.info("更新状态方法执行，参数：reqModel:{}", reqModel);
        applyService.updateState(reqModel);
        return R.ok();
    }
}
