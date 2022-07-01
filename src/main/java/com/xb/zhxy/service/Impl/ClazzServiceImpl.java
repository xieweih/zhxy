package com.xb.zhxy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.zhxy.mapper.ClazzMapper;
import com.xb.zhxy.pojo.Clazz;
import com.xb.zhxy.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    @Override
    public Page<Clazz> getClazzsListByOpr(Page<Clazz> clazzPage, String name, String gradeName) {
        QueryWrapper<Clazz> clazzQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            clazzQueryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(gradeName)){
            clazzQueryWrapper.eq("grade_name",gradeName);
        }
        clazzQueryWrapper.orderByAsc("id");
        return clazzMapper.selectPage(clazzPage,clazzQueryWrapper);
    }

    @Override
    public List<Clazz> getClazzsList() {
        List<Clazz> clazzList = clazzMapper.selectList(null);
        return clazzList;
    }
}
