package com.student.service;

import com.student.model.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student.model.req.StuLoginReqModel;
import com.student.model.req.StudentReqModel;
import com.student.model.resp.StudentResponse;

/**
 * @date 2021/12/15 23:17
 */
public interface StudentService extends IService<Student> {

    /**
     * 根据条件获取学生
     * @param reqModel 条件
     * @return 学生的返回对象
     */
    StudentResponse selectByWrapper(StudentReqModel reqModel);

    /**
     * 学生登陆方法
     * @param loginReqModel 学生对象
     * @return 是否成功
     */
    boolean login(StuLoginReqModel loginReqModel);
}
