package com.blogpress.common.config

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.{Bean, Configuration}

/**
 * mybatis-plus配置
 * @author JY
 */
@Configuration
@MapperScan(basePackages = Array("com.blogpress.*.dao"))
class MybatisPlusConfig {
	
	@Bean def mybatisPlusInterceptor: MybatisPlusInterceptor = {
		val interceptor = new MybatisPlusInterceptor
		interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor)
		interceptor
	}
	
	@Bean def optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor
}
