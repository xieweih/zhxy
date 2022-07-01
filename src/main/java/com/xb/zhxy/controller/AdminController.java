package com.xb.zhxy.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xb.zhxy.pojo.Admin;
import com.xb.zhxy.service.AdminService;
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
@Api("管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    AdminService adminService;

    @ApiOperation("修改或添加管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("教师信息") @RequestBody Admin admin) {
        boolean result = adminService.saveOrUpdate(admin);
        return result ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据ids删除管理员信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@ApiParam("管理员id") @RequestBody List<Integer> ids) {
        boolean result = adminService.removeByIds(ids);
        return result ? Result.ok() : Result.fail();
    }

    @ApiOperation("获取管理员的分页信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@ApiParam("页码") @PathVariable("pageNo") Integer pageNo,
                              @ApiParam("每页记录数") @PathVariable("pageSize") Integer pageSize,
                              @ApiParam("年级名") @RequestParam(value = "adminName", required = false) String adminName) {
        Page<Admin> adminPage = new Page<>(pageNo, pageSize);
        adminPage = adminService.getAdminsListByOpr(adminPage, adminName);
        return Result.ok(adminPage);
    }
}
