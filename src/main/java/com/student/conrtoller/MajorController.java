package com.student.conrtoller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.common.BusinessException;
import com.student.common.R;
import com.student.common.ResultCodeEnum;
import com.student.model.Major;
import com.student.model.req.MajorReqModel;
import com.student.model.resp.MajorResponse;
import com.student.service.CollegeService;
import com.student.service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @date 2021/12/16 22:46
 */
@RestController
@RequestMapping("/major")
@Slf4j
public class MajorController {

    @Resource
    MajorService majorService;

    @Resource
    CollegeService collegeService;

    @GetMapping("/list/{current}/{limit}")
    public R getList(@PathVariable Long current,
                     @PathVariable Long limit, MajorReqModel reqModel) {
        log.info("获取所有专业方法执行，参数：reqModel:{}", reqModel);
        if (limit == null || current == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        LambdaQueryWrapper<Major> wrapper = Wrappers.lambdaQuery();
        if (reqModel != null && StrUtil.isNotBlank(reqModel.getName())) {
            wrapper.like(Major::getName, reqModel.getName());
        }
        if (reqModel != null && StrUtil.isNotBlank(reqModel.getCollegeId())) {
            wrapper.eq(Major::getCollegeId, reqModel.getCollegeId());
        }
        Page<Major> page = new Page<>(reqModel.getCurrent(), reqModel.getLimit());
        Page<Major> majorPage = majorService.page(page, wrapper);
        MajorResponse majorResponse = new MajorResponse();
        List<Major> records = majorPage.getRecords();
        records.forEach(major -> {
            if (major.getCollegeId() != null && StrUtil.isNotBlank(major.getCollegeId())) {
                major.setCollegeName(collegeService.getById(major.getCollegeId()).getName());
            }
        });
        majorResponse.setPages(majorPage.getPages());
        majorResponse.setMajorList(records);
        majorResponse.setTotal(majorPage.getTotal());
        return R.ok(majorResponse);
    }

    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Major major) {
        log.info("新增或修改专业方法执行，参数：major:{}", major);
        if (major == null) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        if (major.getId() == null || StrUtil.isBlank(major.getId())) {
            major.setGmtCreate(new Date());
        }
        major.setGmtModify(new Date());
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
