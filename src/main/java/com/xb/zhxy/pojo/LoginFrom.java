package com.xb.zhxy.pojo;

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
public class LoginFrom{

    private String verifiCode;
    private String username;
    private String password;
    private Integer userType;

}
