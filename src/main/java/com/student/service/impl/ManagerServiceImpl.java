package com.student.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.mapper.ManagerMapper;
import com.student.model.Manager;
import com.student.service.ManagerService;
/**
    @date 2021/12/14 20:48
*/
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService{

}
