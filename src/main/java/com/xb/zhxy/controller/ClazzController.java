package com.xb.zhxy.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xb.zhxy.pojo.Clazz;
import com.xb.zhxy.service.ClazzService;
import com.xb.zhxy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 谢炜宏
 * @version 1.0
 */
@Api("班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @ApiOperation("班级回显")
    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> clazzList = clazzService.getClazzsList();
        return Result.ok(clazzList);
    }


    @ApiOperation("返回分页的班级信息（根据班级模糊查询）")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@ApiParam("页码") @PathVariable("pageNo") Integer pageNo,
                                 @ApiParam("每页记录数") @PathVariable("pageSize") Integer pageSize,
                                 @ApiParam("班级名") @RequestParam(value = "name", required = false) String name,
                                 @ApiParam("年级名") @RequestParam(value = "gradeName", required = false) String gradeName) {
        Page<Clazz> clazzPage = new Page<>(pageNo, pageSize);
        clazzPage = clazzService.getClazzsListByOpr(clazzPage, name, gradeName);
        return Result.ok(clazzPage);
    }

    @ApiOperation("修改或添加班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("班级信息") @RequestBody Clazz clazz) {
        boolean result = clazzService.saveOrUpdate(clazz);
        return result ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据ids删除班级信息")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@ApiParam("班级id") @RequestBody List<Integer> ids) {
        boolean result = clazzService.removeByIds(ids);
        return result ? Result.ok() : Result.fail();
    }

}
