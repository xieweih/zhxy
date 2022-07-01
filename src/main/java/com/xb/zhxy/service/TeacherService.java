package com.xb.zhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.zhxy.pojo.LoginFrom;
import com.xb.zhxy.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
    /**
     * 登录校验
     * @param loginFrom 用户登录信息
     * @return
     */
    Teacher login(LoginFrom loginFrom);
    /**
     * 信息获取，跳转至首页
     * @param userId
     * @return
     */
    Teacher getTeacherById(Long userId);


    /**
     * 获取班级的分页信息
     * @param teacherPage 分页信息
     * @param name 教师名
     * @param clazzName  年级名
     * @return
     */
    Page<Teacher> getTeachersListByOpr(Page<Teacher> teacherPage, String name, String clazzName);
}