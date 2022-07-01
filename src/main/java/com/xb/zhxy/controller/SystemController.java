package com.xb.zhxy.controller;

import com.xb.zhxy.pojo.Admin;
import com.xb.zhxy.pojo.LoginFrom;
import com.xb.zhxy.pojo.Student;
import com.xb.zhxy.pojo.Teacher;
import com.xb.zhxy.service.AdminService;
import com.xb.zhxy.service.StudentService;
import com.xb.zhxy.service.TeacherService;
import com.xb.zhxy.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 谢炜宏
 * @version 1.0
 */
@RestController
@Slf4j
@Api("系统控制器")
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;


    @PostMapping("/updatePwd/{proPassword}/{newPassword}")
    public Result updatePwd(@PathVariable("proPassword") String proPassword,
                            @PathVariable("newPassword") String newPassword,
                            @RequestHeader("token") String token){
//        获取token并进行解析校验
        if (StringUtils.isEmpty(token)){
            return Result.fail().message("请先登录！");
        }
        if (JwtHelper.isExpiration(token)) {
            return Result.build(null,ResultCodeEnum.TOKEN_ERROR);
        }
//        获取用户id
        Long userId = JwtHelper.getUserId(token);
//        获取用户类型
        Integer userType = JwtHelper.getUserType(token);
//        获取新旧密码密文
        proPassword = MD5.encrypt(proPassword);
        newPassword = MD5.encrypt(newPassword);
        try {
//        分用户类型处理
            switch (userType){
    //            管理员：
                case 1:
                    Admin admin = adminService.getAdminById(userId);
    //                通过密码验证
                    if (proPassword.equals(admin.getPassword())){
                        admin.setPassword(newPassword);
                        adminService.saveOrUpdate(admin);
                        return Result.ok();
                    }
                    return Result.fail().message("原密码错误,请重试");
    //            学生：
                case 2:
                    Student student = studentService.getAStudentById(userId);
    //                通过密码验证
                    if (proPassword.equals(student.getPassword())){
                        student.setPassword(newPassword);
                        studentService.saveOrUpdate(student);
                        return Result.ok();
                    }
                    return Result.fail().message("原密码错误,请重试");
    //            教师
                case 3:
                    Teacher teacher = teacherService.getTeacherById(userId);
    //                通过密码验证
                    if (proPassword.equals(teacher.getPassword())){
                        teacher.setPassword(newPassword);
                        teacherService.saveOrUpdate(teacher);
                        return Result.ok();
                    }
                    return Result.fail().message("原密码错误,请重试");
            }
        } catch (Exception e) {
            return Result.fail().message(e.getMessage());
        }
        return Result.fail();
    }

    @ApiOperation("头像上传")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@ApiParam("图片二进制文件")@RequestPart("multipartFile") MultipartFile multipartFile){
//        判断文件是否为空
        if (multipartFile.isEmpty()) {
            return Result.fail().message("文件为空");
        }
//        获取原来文件名
        String originalFilename = multipartFile.getOriginalFilename();
//        获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        生成文件前缀
        String prefix = UUID.randomUUID().toString().replace("-", "");
//        文件存放路径
        String realPath = "C:\\Users\\落尘\\IdeaProjects\\zhxy\\target\\classes\\public\\upload";
//        文件真实存放路径
        String absolutePath = realPath+File.separator+prefix + suffix;
        try {
            multipartFile.transferTo(new File(absolutePath));
        } catch (IOException e) {
            return Result.fail().message(e.getMessage());
        }
        return Result.ok("upload/"+prefix+suffix);
    }


    /**
     * 进行携带用户信息的登录页面跳转
     *
     * @param token
     * @return
     */
    @GetMapping("/getInfo")
    @ApiOperation("进行携带用户信息的登录页面跳转")
    public Result getInfo(@ApiParam("token") @RequestHeader("token") String token) {
//        判断token是否为空
        if (StringUtils.isEmpty(token)) {
            return Result.fail().message("请先登录！！");
        }
//        判断token是否过期
        if (JwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
//        解析token
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
//        结果集
        HashMap<String, Object> valMap = new HashMap<>();
//        根据用户类型获取对应信息
        switch (userType) {
            case 1:
                Admin admin =  adminService.getAdminById(userId);
                valMap.put("user",admin);
                valMap.put("userType",userType);
                break;
            case 2:
                Student student =  studentService.getAStudentById(userId);
                valMap.put("user",student);
                valMap.put("userType",userType);
                break;
            case 3:
                Teacher teacher =  teacherService.getTeacherById(userId);
                valMap.put("user",teacher);
                valMap.put("userType",userType);
                break;
        }
        return Result.ok(valMap);
    }

    /**
     * 用户登录处理
     *
     * @param loginFrom 用户登录的请求体
     * @param request   用于获取Session
     * @return 登录的结果
     */
    @PostMapping("/login")
    @ApiOperation("用户登录处理")
    public Result login(@ApiParam("用户登录的请求信息")@RequestBody LoginFrom loginFrom,
                        @ApiParam("请求") HttpServletRequest request) {
        String loginFromVerifiCode = loginFrom.getVerifiCode();
        String userVerifiCode = (String) (request.getSession().getAttribute("verifiCode"));
//        判断loginFromVerifiCode是否为空
        if (StringUtils.isEmpty(loginFromVerifiCode)) {
            return Result.fail().message("验证码不能为空，请输入验证码");
        }
        log.info("loginFromVerifiCode:{}\tuserVerifiCode:{}", loginFromVerifiCode, userVerifiCode);
//        判断验证码是否相等,记得忽略大小写equalsIgnoreCase
        if (!loginFromVerifiCode.equalsIgnoreCase(userVerifiCode)) {
            return Result.fail().message("验证码有误，请重新输入验证码");
        }
//        从session域中删除验证码
        request.getSession().removeAttribute("verifiCode");
        String username = loginFrom.getUsername();
        String password = loginFrom.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Result.fail().message("账号或密码不能为空");
        }
//        结果集
        Map<String, Object> resultMap = new HashMap<>();
//        判断用户类型进行登录
        switch (loginFrom.getUserType()) {
//            管理员登录：
            case 1:
                try {
                    Admin login = adminService.login(loginFrom);
                    if (login != null) {
                        //                    生成Token
                        String token = JwtHelper.createToken(login.getId().longValue(), loginFrom.getUserType());
                        resultMap.put("token", token);
                        return Result.ok(resultMap).message("登录成功！");
                    } else {
                        throw new RuntimeException("账号或密码有误");
                    }
                } catch (Exception e) {
                    return Result.fail().message(e.getMessage());
                }
//            学生登录：
            case 2:
                try {
                    Student student = studentService.login(loginFrom);
                    if (student != null) {
                        //                    生成Token
                        String token = JwtHelper.createToken(student.getId().longValue(), loginFrom.getUserType());
                        resultMap.put("token", token);
                        return Result.ok(resultMap).message("登录成功！");
                    } else {
                        throw new RuntimeException("账号或密码有误");
                    }
                } catch (RuntimeException e) {
                    return Result.fail().message(e.getMessage());
                }
//            教师登录：
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginFrom);
                    if (teacher != null) {
                        //                    生成Token
                        String token = JwtHelper.createToken(teacher.getId().longValue(), loginFrom.getUserType());
                        resultMap.put("token", token);
                        return Result.ok(resultMap).message("登录成功！");
                    } else {
                        throw new RuntimeException("账号或密码有误");
                    }
                } catch (Exception e) {
                    return Result.fail().message(e.getMessage());
                }
        }
//        登入失败处理
        return Result.fail().message("查无此人，登录失败");
    }


    @GetMapping("/getVerifiCodeImage")
    @ApiOperation("获取验证码")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
//        获取图片
        BufferedImage image = CreateVerifiCodeImage.getVerifiCodeImage();
//        获取图片的验证码
        String code = new String(CreateVerifiCodeImage.getVerifiCode());
//        将验证码放入请求域中
        request.getSession().setAttribute("verifiCode", code);
//        将图片作为输出流输出至前端
        try {
            ImageIO.write(image, "JPG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
