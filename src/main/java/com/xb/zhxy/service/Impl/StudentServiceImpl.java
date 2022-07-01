package com.xb.zhxy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.zhxy.mapper.StudentMapper;
import com.xb.zhxy.pojo.LoginFrom;
import com.xb.zhxy.pojo.Student;
import com.xb.zhxy.pojo.Teacher;
import com.xb.zhxy.service.StudentService;
import com.xb.zhxy.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 谢炜宏
 * @version 1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student login(LoginFrom loginFrom) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("name",loginFrom.getUsername())
                .eq("password", MD5.encrypt(loginFrom.getPassword()));
        return studentMapper.selectOne(studentQueryWrapper);
    }

    @Override
    public Student getAStudentById(Long userId) {
        return studentMapper.selectById(userId);
    }

    @Override
    public Page<Student> getStudentListByOpr(Page<Student> studentPage, String name, String clazzName) {
        QueryWrapper<Student> teacherQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            teacherQueryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(clazzName)){
            teacherQueryWrapper.eq("clazz_name",clazzName);
        }
        return studentMapper.selectPage(studentPage,teacherQueryWrapper);
    }
}
