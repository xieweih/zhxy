package com.xb.zhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.zhxy.pojo.Admin;
import com.xb.zhxy.pojo.LoginFrom;

/**
 * @author 谢炜宏
 * @version 1.0
 */
public interface AdminService extends IService<Admin> {
    /**
     * 登录校验
     * @param loginFrom 用户登录信息
     * @return
     */
     Admin login(LoginFrom loginFrom);

    /**
     * 信息获取，跳转至首页
     * @param userId
     * @return
     */
    Admin getAdminById(Long userId);

    /**
     * 返回管理员的分页信息
     * @param adminPage
     * @param adminName
     * @return
     */
    Page<Admin> getAdminsListByOpr(Page<Admin> adminPage, String adminName);
}
