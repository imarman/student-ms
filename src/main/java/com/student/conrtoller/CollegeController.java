package com.student.conrtoller;

import cn.hutool.core.util.StrUtil;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.College;
import com.student.service.CollegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @date 2021/12/16 21:43
 */
@RestController
@RequestMapping("/college")
@Slf4j
public class CollegeController {

    @Resource
    CollegeService collegeService;

    @GetMapping("/list")
    public R getList() {
        return R.ok(collegeService.list());
    }

    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody College college) {
        log.info("学院的新增或修改方法执行，参数：college:{}", college);
        if (college == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        return collegeService.saveOrUpdate(college) ? R.ok() : R.error();
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") String collegeId) {
        log.info("学院的删除方法执行，参数：collegeId:{}", collegeId);
        if (collegeId == null || StrUtil.isBlank(collegeId)) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        return collegeService.removeById(collegeId) ? R.ok("删除成功") : R.error("删除失败");
    }

}
