package com.xb.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 谢炜宏
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_student")
public class Student {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String sno;
    private String name;
    private Character gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String introducation;
    private String portraitPath;
    private String clazzName;
}
