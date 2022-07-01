package com.xb.zhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.zhxy.pojo.Clazz;

import java.util.List;


public interface ClazzService extends IService<Clazz> {
    /**
     * 获取班级分页列表
     * @param clazzPage 分页信息
     * @param name 班级名
     * @param gradeName 年级名
     * @return
     */
    Page<Clazz> getClazzsListByOpr(Page<Clazz> clazzPage, String name, String gradeName);

    /**
     * 获取班级列表
     * @return
     */
    List<Clazz> getClazzsList();
}
