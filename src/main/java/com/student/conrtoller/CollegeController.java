package com.student.conrtoller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.College;
import com.student.model.Major;
import com.student.service.CollegeService;
import com.student.service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @date 2021/12/16 21:43
 */
@RestController
@RequestMapping("/college")
@Slf4j
public class CollegeController {

    @Resource
    CollegeService collegeService;

    @Resource
    MajorService majorService;

    @GetMapping("/list")
    public R getList(String name) {
        LambdaQueryWrapper<College> wrapper = null;
        if (name != null && StrUtil.isNotBlank(name)) {
            wrapper = Wrappers.lambdaQuery();
            wrapper.like(College::getName, name);
        }
        return R.ok(collegeService.list(wrapper));
    }

    @GetMapping("/listByCollege")
    public R getListByCollegeId(String collegeId) {
        LambdaQueryWrapper<Major> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Major::getCollegeId, collegeId);
        return R.ok(majorService.list(wrapper));
    }

    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody College college) {
        log.info("学院的新增或修改方法执行，参数：college:{}", college);
        if (college == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        if (college.getId() == null || StrUtil.isBlank(college.getId())) {
            college.setGmtCreate(new Date());
        }
        college.setGmtModify(new Date());
        return collegeService.saveOrUpdate(college) ? R.ok() : R.error();
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") String collegeId) {
        log.info("学院的删除方法执行，参数：collegeId:{}", collegeId);
        if (collegeId == null || StrUtil.isBlank(collegeId)) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        // 如果有专业关联，就不能删除
        LambdaQueryWrapper<Major> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Major::getCollegeId, collegeId);
        Major major = majorService.getOne(queryWrapper);
        if (major != null) {
            throw new BusinessException("该学院有关联的专业，请取消关联在删除～");
        }
        return collegeService.removeById(collegeId) ? R.ok("删除成功") : R.error("删除失败");
    }

}
