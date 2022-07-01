package com.xb.zhxy.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.zhxy.mapper.TeacherMapper;
import com.xb.zhxy.pojo.LoginFrom;
import com.xb.zhxy.pojo.Teacher;
import com.xb.zhxy.service.TeacherService;
import com.xb.zhxy.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 谢炜宏
 * @version 1.0
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper,Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(LoginFrom loginFrom) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("name",loginFrom.getUsername())
                .eq("password", MD5.encrypt(loginFrom.getPassword()));
        return teacherMapper.selectOne(wrapper);
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        return teacherMapper.selectById(userId);
    }

    @Override
    public Page<Teacher> getTeachersListByOpr(Page<Teacher> teacherPage, String name, String clazzName) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            teacherQueryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(clazzName)){
            teacherQueryWrapper.eq("clazz_name",clazzName);
        }
        return teacherMapper.selectPage(teacherPage,teacherQueryWrapper);
    }
}
