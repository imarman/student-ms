package com.student.conrtoller;

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
        if (college == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        return collegeService.saveOrUpdate(college) ? R.ok() : R.error();
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") String collegeId) {
        return collegeService.removeById(collegeId) ? R.ok() : R.error();
    }

}
