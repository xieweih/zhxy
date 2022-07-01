package com.xb.zhxy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.zhxy.mapper.AdminMapper;
import com.xb.zhxy.pojo.Admin;
import com.xb.zhxy.pojo.LoginFrom;
import com.xb.zhxy.pojo.Teacher;
import com.xb.zhxy.service.AdminService;
import com.xb.zhxy.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author 谢炜宏
 * @version 1.0
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(LoginFrom loginFrom) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("name",loginFrom.getUsername())
                .eq("password", MD5.encrypt(loginFrom.getPassword()));
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public Admin getAdminById(Long userId) {
        return adminMapper.selectById(userId);
    }

    @Override
    public Page<Admin> getAdminsListByOpr(Page<Admin> adminPage, String adminName) {
        QueryWrapper<Admin> teacherQueryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(adminName)){
            teacherQueryWrapper.like("name",adminName);
        }
        return adminMapper.selectPage(adminPage,teacherQueryWrapper);
    }
}

