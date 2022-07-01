package com.xb.zhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.zhxy.pojo.LoginFrom;
import com.xb.zhxy.pojo.Student;


public interface StudentService extends IService<Student> {
    /**
     * 登录校验
     * @param loginFrom 用户登录信息
     * @return
     */
    Student login(LoginFrom loginFrom);
    /**
     * 信息获取，跳转至首页
     * @param userId
     * @return
     */
    Student getAStudentById(Long userId);

    /**
     * 获取分页的学生信息列表
     * @param studentPage 分页信息
     * @param name 学生名称
     * @param clazzName 班级名称
     * @return
     */
    Page<Student> getStudentListByOpr(Page<Student> studentPage, String name, String clazzName);
}

