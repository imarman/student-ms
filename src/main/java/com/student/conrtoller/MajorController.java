package com.student.conrtoller;

import cn.hutool.core.util.StrUtil;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.Major;
import com.student.service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @date 2021/12/16 22:46
 */
@RestController
@RequestMapping("/major")
@Slf4j
public class MajorController {

    @Resource
    MajorService majorService;

    @GetMapping("/list")
    public R getList() {
        return R.ok(majorService.list());
    }

    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Major major) {
        log.info("新增或修改专业方法执行，参数：major:{}", major);
        if (major == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        return majorService.saveOrUpdate(major) ? R.ok() : R.error("操作失败");
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") String majorId) {
        log.info("删除专业方法执行，参数：majorId:{}", majorId);
        if (majorId == null || StrUtil.isBlank(majorId)) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        return majorService.removeById(majorId) ? R.ok("删除成功") : R.error("删除失败");
    }

}
