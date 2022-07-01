package com.xb.zhxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xb.zhxy.pojo.Grade;
import com.xb.zhxy.service.GradeService;
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
@RestController
@RequestMapping("/sms/gradeController")
@Api("年级控制器")
public class GradeController {

    @Autowired
    private GradeService gradeService;


    @ApiOperation("年级回显")
    @GetMapping("/getGrades")
    public Result getGrades(){
       List<Grade> grades =  gradeService.getGradeList();
       return Result.ok(grades);
    }


    /**
     * 根据给定id删除或批量删除
     * @param ids ids
     * @return
     */
    @ApiOperation("根据给定id删除或批量删除年级信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("id列表") @RequestBody List<Integer> ids) {
        boolean result = gradeService.removeByIds(ids);
        return result ? Result.ok() : Result.fail();
    }

    /**
     * 添加或者修改
     * id为空---->添加
     * id不为空----->修改
     *
     * @param grade
     * @return
     */
    @ApiOperation("添加或者修改年级信息")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("待添加或修改的年级信息")@RequestBody Grade grade) {
        boolean result = gradeService.saveOrUpdate(grade);
        return result ? Result.ok() : Result.fail();
    }

    /**
     * 年级的（模糊查询）分页显示
     *
     * @param pageNo    页码
     * @param pageSize  页数据条数
     * @param gradeName 年级名称
     * @return
     */
    @ApiOperation("年级的（模糊查询）分页显示")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@ApiParam("页码")@PathVariable("pageNo") Integer pageNo,
                            @ApiParam("页数据条数")@PathVariable("pageSize") Integer pageSize,
                            @ApiParam("年级名称")@RequestParam(value = "gradeName", required = false) String gradeName) {
        Page<Grade> gradePage = new Page<>(pageNo, pageSize);
        gradePage = gradeService.getGradeByOpr(gradePage, gradeName);
        return Result.ok(gradePage);
    }
}
