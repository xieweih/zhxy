package com.xb.zhxy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.zhxy.mapper.GradeMapper;
import com.xb.zhxy.pojo.Grade;
import com.xb.zhxy.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 谢炜宏
 * @version 1.0
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Autowired
    private  GradeMapper gradeMapper;
    @Override
    public Page<Grade> getGradeByOpr(Page<Grade> gradePage, String gradeName) {
        QueryWrapper<Grade> gradeQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)){
            gradeQueryWrapper.like("name",gradeName);
        }
        gradeQueryWrapper.orderByAsc("id");
       return gradeMapper.selectPage(gradePage,gradeQueryWrapper);
    }

    @Override
    public List<Grade> getGradeList() {
        List<Grade> grades = gradeMapper.selectList(null);
        return grades;
    }
}
