package com.xb.zhxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xb.zhxy.pojo.Student;
import com.xb.zhxy.pojo.Teacher;
import com.xb.zhxy.service.StudentService;
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
@Api("学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    StudentService studentService;

    @ApiOperation("修改或添加学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@ApiParam("学生信息") @RequestBody Student student) {
        boolean result = studentService.saveOrUpdate(student);
        return result ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据ids删除学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@ApiParam("学生id") @RequestBody List<Integer> ids) {
        boolean result = studentService.removeByIds(ids);
        return result ? Result.ok() : Result.fail();
    }

    @ApiOperation("返回对应条件的学生分页信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@ApiParam("页码") @PathVariable("pageNo") Integer pageNo,
                                  @ApiParam("页记录数") @PathVariable("pageSize") Integer pageSize,
                                  @ApiParam("学生名称") @RequestParam(value = "name",required = false) String name,
                                  @ApiParam("教室名称") @RequestParam(value = "clazzName",required = false )String clazzName){
        Page<Student> studentPage = new Page<>(pageNo,pageSize);
        studentPage = studentService.getStudentListByOpr(studentPage,name,clazzName);
        return Result.ok(studentPage);
    }
}
