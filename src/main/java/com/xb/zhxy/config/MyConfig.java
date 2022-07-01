package com.xb.zhxy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 谢炜宏
 * @version 1.0
 * 配置类
 */

@Configuration(proxyBeanMethods=false)
//用于扫描mapper接口
@MapperScan("com.xb.zhxy.mapper")
public class MyConfig {
    /**
     * @return mybatis-plus分页插件配置类
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor interceptor = new PaginationInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        return interceptor;
    }
}
