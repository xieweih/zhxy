package com.xb.zhxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xb.zhxy.pojo.Teacher;
import com.xb.zhxy.service.TeacherService;
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
@Api("老师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    TeacherService teacherService;


    @ApiOperation("修改或添加教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@ApiParam("教师信息") @RequestBody Teacher teacher) {
        boolean result = teacherService.saveOrUpdate(teacher);
        return result ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据ids删除教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@ApiParam("教师id") @RequestBody List<Integer> ids) {
        boolean result = teacherService.removeByIds(ids);
        return result ? Result.ok() : Result.fail();
    }

    @ApiOperation("获取老师的分页信息")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@ApiParam("页码") @PathVariable("pageNo") Integer pageNo,
                              @ApiParam("每页记录数") @PathVariable("pageSize") Integer pageSize,
                              @ApiParam("教师名") @RequestParam(value = "name", required = false) String name,
                              @ApiParam("年级名") @RequestParam(value = "clazzName", required = false) String clazzName) {
        Page<Teacher> teacherPage = new Page<>(pageNo, pageSize);
        teacherPage = teacherService.getTeachersListByOpr(teacherPage, name, clazzName);
        return Result.ok(teacherPage);
    }
}
