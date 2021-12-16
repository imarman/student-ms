package com.student.model.resp;

import com.student.model.Student;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2021/12/16 21:14
 */
@Data
public class StudentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Student> studentList;

    /**
     * 总数据
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pages;

}
