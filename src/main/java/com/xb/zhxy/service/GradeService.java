package com.xb.zhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.zhxy.pojo.Grade;

import java.util.List;

public interface GradeService extends IService<Grade> {
    /**
     * 降序分页查询
     * @param gradePage 分析信息
     * @param gradeName 年级名
     * @return
     */
    Page<Grade> getGradeByOpr(Page<Grade> gradePage, String gradeName);

    /**
     * 获取年级列表
     * @return
     */
    List<Grade> getGradeList();
}
